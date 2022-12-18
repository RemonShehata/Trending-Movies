package com.example.trendingmovies.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.util.Log
import com.example.trendingmovies.TAG
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


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
                networkMutableStateFlow.value = NetworkState.Connected
            }

            override fun onLost(network: Network) {
                // network unavailable
                Log.d(TAG, "onLost: ")
                networkMutableStateFlow.value = NetworkState.Disconnected
            }
        }

        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    private fun getNetworkState(): NetworkState {
        val conMgr =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = conMgr.activeNetworkInfo

        return if (activeNetwork != null && activeNetwork.isConnected) {
            Log.d(TAG, "getNetworkState: Connected")
            NetworkState.Connected
        } else {
            Log.d(TAG, "getNetworkState: Disconnected")
            NetworkState.Disconnected
        }
    }
}
