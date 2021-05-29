package com.razit.moviecatalogue.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities


object Constanta {
    const val DELAY_GET_DATA = 2000L
    const val DELAY_SPLASH = 3000L
    var DATA_EXIST = false
    var DETAIL_EXIST = false

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}