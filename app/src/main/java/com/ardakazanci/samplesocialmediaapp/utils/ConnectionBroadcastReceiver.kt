package com.ardakazanci.samplesocialmediaapp.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

abstract class ConnectionBroadcastReceiver : BroadcastReceiver() {
    companion object {
        @JvmStatic
        fun registerWithoutAutoUnregister(
            context: Context,
            connectionBroadcastReceiver: ConnectionBroadcastReceiver
        ) {
            context.registerReceiver(
                connectionBroadcastReceiver,
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            )
        }

        @JvmStatic
        fun registerToFragmentAndAutoUnregister(
            context: Context,
            fragment: Fragment,
            connectionBroadcastReceiver: ConnectionBroadcastReceiver
        ) {
            val applicationContext = context.applicationContext
            registerWithoutAutoUnregister(applicationContext, connectionBroadcastReceiver)
            fragment.lifecycle.addObserver(object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun onDestroy() {
                    applicationContext.unregisterReceiver(connectionBroadcastReceiver)
                }
            })
        }

        @JvmStatic
        fun registerToActivityAndAutoUnregister(
            activity: AppCompatActivity,
            connectionBroadcastReceiver: ConnectionBroadcastReceiver
        ) {
            registerWithoutAutoUnregister(activity, connectionBroadcastReceiver)
            activity.lifecycle.addObserver(object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun onDestroy() {
                    activity.unregisterReceiver(connectionBroadcastReceiver)
                }
            })
        }

        @JvmStatic
        fun hasInternetConnection(context: Context): Boolean {
            val info =
                (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
            return !(info == null || !info.isConnectedOrConnecting)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        val hasConnection =
            !intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)
//        Log.d("AppLog", "conenctivity changed. hasConnection? $hasConnection")
        onConnectionChanged(hasConnection)
    }

    abstract fun onConnectionChanged(hasConnection: Boolean)
}