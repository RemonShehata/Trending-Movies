package com.example.trendingmovies.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.util.Log
import com.example.trendingmovies.base.TAG
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.IOException


sealed class NetworkState {
    object Connected : NetworkState()
    object Disconnected : NetworkState()
}

class NetworkStateMonitor(private val applicationContext: Context) {

    private val networkMutableStateFlow: MutableStateFlow<NetworkState> =
        MutableStateFlow(getNetworkState())


    val networkStateFlow: StateFlow<NetworkState> = networkMutableStateFlow.asStateFlow()

    private val connectivityManager by lazy {
        applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    init {
        val networkCallback: NetworkCallback = object : NetworkCallback() {
            override fun onAvailable(network: Network) {
                // network available
                Log.d(TAG, "onAvailable: ")
//                if (pingServer()){
                    networkMutableStateFlow.value = NetworkState.Connected
//                } else {
//                    networkMutableStateFlow.value = NetworkState.Disconnected
//                }
            }

            override fun onLost(network: Network) {
                // network unavailable
                Log.d(TAG, "onLost: ")
                networkMutableStateFlow.value = NetworkState.Disconnected
            }
        }

        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    fun getNetworkState(): NetworkState {
        val conMgr =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = conMgr.activeNetworkInfo

        return if (activeNetwork != null && activeNetwork.isConnected) {
            Log.d(TAG, "getNetworkState: Connected")
//            val result = pingServer()
//            Log.d(TAG, "getNetworkState: pingServer = $result")
            NetworkState.Connected
        } else {
            Log.d(TAG, "getNetworkState: Disconnected")
            NetworkState.Disconnected
        }
    }

    // ICMP
//    private fun pingServer(): Boolean {
//        val runtime = Runtime.getRuntime()
//        try {
//            val ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
//            val exitValue = ipProcess.waitFor()
//            return exitValue == 0
//        } catch (e: IOException) {
//            e.printStackTrace()
//        } catch (e: InterruptedException) {
//            e.printStackTrace()
//        }
//        return false
//    }
}
