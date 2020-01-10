package com.ardakazanci.samplesocialmediaapp.ui.main.ui.home.datasource

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.ardakazanci.samplesocialmediaapp.data.model.Doc
import com.ardakazanci.samplesocialmediaapp.data.network.ApiService
import com.ardakazanci.samplesocialmediaapp.data.network.IApiInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class ContentDataSource(coroutineContext: CoroutineContext) :
    PageKeyedDataSource<Int, Doc>() {


    private val apiService = ApiService.getRetrofit().create(IApiInterface::class.java)

    private val job = Job()
    private val scope = CoroutineScope(coroutineContext + job)


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Doc>
    ) {

        scope.launch {
            try {
                val response = apiService.fetchContents(loadSize = params.requestedLoadSize)
                when {
                    response.isSuccessful -> {

                        val listing = response.body()?.content
                        val contentPosts = listing?.docs



                        callback.onResult(contentPosts ?: listOf(), listing?.before, listing?.after)
                    }
                }

            } catch (exception: Exception) {
                Log.e("ContentDataSource", "Failed to fetch data!")
            }

        }


    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Doc>) {
        scope.launch {
            try {
                val response =
                    apiService.fetchContents(
                        loadSize = params.requestedLoadSize,
                        after = params.key
                    )
                when {
                    response.isSuccessful -> {
                        val listing = response.body()?.content
                        val items = listing?.docs
                        callback.onResult(items ?: listOf(), listing?.after)
                    }
                }

            } catch (exception: Exception) {
                Log.e("PostsDataSource", "Failed to fetch data!")
            }
        }

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Doc>) {
        scope.launch {
            try {
                val response =
                    apiService.fetchContents(
                        loadSize = params.requestedLoadSize,
                        before = params.key
                    )
                when {
                    response.isSuccessful -> {
                        val listing = response.body()?.content
                        val items = listing?.docs
                        callback.onResult(items ?: listOf(), listing?.after)
                    }
                }

            } catch (exception: Exception) {
                Log.e("PostsDataSource", "Failed to fetch data!")
            }
        }

    }

    override fun invalidate() {
        super.invalidate()
        job.cancel()
    }


}