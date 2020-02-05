/*
 * Copyright (c) 2018 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.ardakazanci.anlikmotivasyon.utils;


import androidx.annotation.AnyThread;
import androidx.annotation.GuardedBy;
import androidx.annotation.VisibleForTesting;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.annotations.*;

public class PagingRequestHelper {
    @NonNull
    final CopyOnWriteArrayList<Listener> mListeners = new CopyOnWriteArrayList<>();
    private final Object mLock = new Object();
    private final Executor mRetryService;
    @GuardedBy("mLock")
    private final RequestQueue[] mRequestQueues = new RequestQueue[]
            {new RequestQueue(RequestType.INITIAL),
                    new RequestQueue(RequestType.BEFORE),
                    new RequestQueue(RequestType.AFTER)};

    /**
     * Creates a new PagingRequestHelper with the given {@link Executor} which is used to run
     * retry actions.
     *
     * @param retryService The {@link Executor} that can run the retry actions.
     */
    public PagingRequestHelper(@NonNull Executor retryService) {
        mRetryService = retryService;
    }

    /**
     * Adds a new listener that will be notified when any request changes {@link Status state}.
     *
     * @param listener The listener that will be notified each time a request's status changes.
     * @return True if it is added, false otherwise (e.g. it already exists in the list).
     */
    @AnyThread
    public boolean addListener(@NonNull Listener listener) {
        return mListeners.add(listener);
    }

    /**
     * Removes the given listener from the listeners list.
     *
     * @param listener The listener that will be removed.
     * @return True if the listener is removed, false otherwise (e.g. it never existed)
     */
    public boolean removeListener(@NonNull Listener listener) {
        return mListeners.remove(listener);
    }

    /**
     * Runs the given {@link Request} if no other requests in the given request type is already
     * running.
     * <p>
     * If run, the request will be run in the current thread.
     *
     * @param type    The type of the request.
     * @param request The request to run.
     * @return True if the request is run, false otherwise.
     */
    @SuppressWarnings("WeakerAccess")
    @AnyThread
    public boolean runIfNotRunning(@NonNull RequestType type, @NonNull Request request) {
        boolean hasListeners = !mListeners.isEmpty();
        StatusReport report = null;
        synchronized (mLock) {
            RequestQueue queue = mRequestQueues[type.ordinal()];
            if (queue.mRunning != null) {
                return false;
            }
            queue.mRunning = request;
            queue.mStatus = Status.RUNNING;
            queue.mFailed = null;
            queue.mLastError = null;
            if (hasListeners) {
                report = prepareStatusReportLocked();
            }
        }
        if (report != null) {
            dispatchReport(report);
        }
        final RequestWrapper wrapper = new RequestWrapper(request, this, type);
        wrapper.run();
        return true;
    }

    @GuardedBy("mLock")
    private StatusReport prepareStatusReportLocked() {
        Throwable[] errors = new Throwable[]{
                mRequestQueues[0].mLastError,
                mRequestQueues[1].mLastError,
                mRequestQueues[2].mLastError
        };
        return new StatusReport(
                getStatusForLocked(RequestType.INITIAL),
                getStatusForLocked(RequestType.BEFORE),
                getStatusForLocked(RequestType.AFTER),
                errors
        );
    }

    @GuardedBy("mLock")
    private Status getStatusForLocked(RequestType type) {
        return mRequestQueues[type.ordinal()].mStatus;
    }

    @AnyThread
    @VisibleForTesting
    void recordResult(@NonNull RequestWrapper wrapper, @Nullable Throwable throwable) {
        StatusReport report = null;
        final boolean success = throwable == null;
        boolean hasListeners = !mListeners.isEmpty();
        synchronized (mLock) {
            RequestQueue queue = mRequestQueues[wrapper.mType.ordinal()];
            queue.mRunning = null;
            queue.mLastError = throwable;
            if (success) {
                queue.mFailed = null;
                queue.mStatus = Status.SUCCESS;
            } else {
                queue.mFailed = wrapper;
                queue.mStatus = Status.FAILED;
            }
            if (hasListeners) {
                report = prepareStatusReportLocked();
            }
        }
        if (report != null) {
            dispatchReport(report);
        }
    }

    private void dispatchReport(StatusReport report) {
        for (Listener listener : mListeners) {
            listener.onStatusChange(report);
        }
    }

    /**
     * Retries all failed requests.
     *
     * @return True if any request is retried, false otherwise.
     */
    public boolean retryAllFailed() {
        final RequestWrapper[] toBeRetried = new RequestWrapper[RequestType.values().length];
        boolean retried = false;
        synchronized (mLock) {
            for (int i = 0; i < RequestType.values().length; i++) {
                toBeRetried[i] = mRequestQueues[i].mFailed;
                mRequestQueues[i].mFailed = null;
            }
        }
        for (RequestWrapper failed : toBeRetried) {
            if (failed != null) {
                failed.retry(mRetryService);
                retried = true;
            }
        }
        return retried;
    }

    /**
     * Represents the status of a Request for each {@link RequestType}.
     */
    public enum Status {
        /**
         * There is current a running request.
         */
        RUNNING,
        /**
         * The last request has succeeded or no such requests have ever been run.
         */
        SUCCESS,
        /**
         * The last request has failed.
         */
        FAILED
    }

    /**
     * Available request types.
     */
    public enum RequestType {

        INITIAL,

        BEFORE,

        AFTER
    }

    /**
     * Runner class that runs a request tracked by the {@link PagingRequestHelper}.
     * <p>
     * When a request is invoked, it must call one of {@link Callback#recordFailure(Throwable)}
     * or {@link Callback#recordSuccess()} once and only once. This call
     * can be made any time. Until that method call is made, {@link PagingRequestHelper} will
     * consider the request is running.
     */
    @FunctionalInterface
    public interface Request {
        /**
         * Should run the request and call the given {@link Callback} with the result of the
         * request.
         *
         * @param callback The callback that should be invoked with the result.
         */
        void run(Callback callback);

        /**
         * Callback class provided to the {@link #run(Callback)} method to report the result.
         */
        class Callback {
            private final AtomicBoolean mCalled = new AtomicBoolean();
            private final RequestWrapper mWrapper;
            private final PagingRequestHelper mHelper;

            Callback(RequestWrapper wrapper, PagingRequestHelper helper) {
                mWrapper = wrapper;
                mHelper = helper;
            }

            /**
             * Call this method when the request succeeds and new data is fetched.
             */
            @SuppressWarnings("unused")
            public final void recordSuccess() {
                if (mCalled.compareAndSet(false, true)) {
                    mHelper.recordResult(mWrapper, null);
                } else {
                    throw new IllegalStateException(
                            "already called recordSuccess or recordFailure");
                }
            }

            /**
             * Call this method with the failure message and the request can be retried via
             * {@link #retryAllFailed()}.
             *
             * @param throwable The error that occured while carrying out the request.
             */
            @SuppressWarnings("unused")
            public final void recordFailure(@NonNull Throwable throwable) {
                //noinspection ConstantConditions
                if (throwable == null) {
                    throw new IllegalArgumentException("You must provide a throwable describing"
                            + " the error to record the failure");
                }
                if (mCalled.compareAndSet(false, true)) {
                    mHelper.recordResult(mWrapper, throwable);
                } else {
                    throw new IllegalStateException(
                            "already called recordSuccess or recordFailure");
                }
            }
        }
    }

    /**
     * Listener interface to get notified by request status changes.
     */
    public interface Listener {
        /**
         * Called when the status for any of the requests has changed.
         *
         * @param report The current status report that has all the information about the requests.
         */
        void onStatusChange(@NonNull StatusReport report);
    }

    static class RequestWrapper implements Runnable {
        @NonNull
        final Request mRequest;
        @NonNull
        final PagingRequestHelper mHelper;
        @NonNull
        final RequestType mType;

        RequestWrapper(@NonNull Request request, @NonNull PagingRequestHelper helper,
                       @NonNull RequestType type) {
            mRequest = request;
            mHelper = helper;
            mType = type;
        }

        @Override
        public void run() {
            mRequest.run(new Request.Callback(this, mHelper));
        }

        void retry(Executor service) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    mHelper.runIfNotRunning(mType, mRequest);
                }
            });
        }
    }

    /**
     * Data class that holds the information about the current status of the ongoing requests
     * using this helper.
     */
    public static final class StatusReport {
        /**
         * Status of the latest request that were submitted with {@link RequestType#INITIAL}.
         */
        @NonNull
        public final Status initial;
        /**
         * Status of the latest request that were submitted with {@link RequestType#BEFORE}.
         */
        @NonNull
        public final Status before;
        /**
         * Status of the latest request that were submitted with {@link RequestType#AFTER}.
         */
        @NonNull
        public final Status after;
        @NonNull
        private final Throwable[] mErrors;

        StatusReport(@NonNull Status initial, @NonNull Status before, @NonNull Status after,
                     @NonNull Throwable[] errors) {
            this.initial = initial;
            this.before = before;
            this.after = after;
            this.mErrors = errors;
        }

        /**
         * Convenience method to check if there are any running requests.
         *
         * @return True if there are any running requests, false otherwise.
         */
        public boolean hasRunning() {
            return initial == Status.RUNNING
                    || before == Status.RUNNING
                    || after == Status.RUNNING;
        }

        /**
         * Convenience method to check if there are any requests that resulted in an error.
         *
         * @return True if there are any requests that finished with error, false otherwise.
         */
        public boolean hasError() {
            return initial == Status.FAILED
                    || before == Status.FAILED
                    || after == Status.FAILED;
        }

        /**
         * Returns the error for the given request type.
         *
         * @param type The request type for which the error should be returned.
         * @return The {@link Throwable} returned by the failing request with the given type or
         * {@code null} if the request for the given type did not fail.
         */
        @Nullable
        public Throwable getErrorFor(@NonNull RequestType type) {
            return mErrors[type.ordinal()];
        }

        @Override
        public String toString() {
            return "StatusReport{"
                    + "initial=" + initial
                    + ", before=" + before
                    + ", after=" + after
                    + ", mErrors=" + Arrays.toString(mErrors)
                    + '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            StatusReport that = (StatusReport) o;
            if (initial != that.initial) return false;
            if (before != that.before) return false;
            if (after != that.after) return false;
            // Probably incorrect - comparing Object[] arrays with Arrays.equals
            return Arrays.equals(mErrors, that.mErrors);
        }

        @Override
        public int hashCode() {
            int result = initial.hashCode();
            result = 31 * result + before.hashCode();
            result = 31 * result + after.hashCode();
            result = 31 * result + Arrays.hashCode(mErrors);
            return result;
        }
    }

    class RequestQueue {
        @NonNull
        final RequestType mRequestType;
        @Nullable
        RequestWrapper mFailed;
        @Nullable
        Request mRunning;
        @Nullable
        Throwable mLastError;
        @NonNull
        Status mStatus = Status.SUCCESS;

        RequestQueue(@NonNull RequestType requestType) {
            mRequestType = requestType;
        }
    }
}
