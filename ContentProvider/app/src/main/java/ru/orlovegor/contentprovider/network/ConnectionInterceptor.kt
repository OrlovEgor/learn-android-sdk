package ru.orlovegor.contentprovider.network

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectionInterceptor(context: Context) : Interceptor {

    private val _context: Context = context

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        return if (!isConnected()) {
            Log.d("app", "throw exception")
            throw IOException()
        } else {
            chain.proceed(originalRequest)
        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager =
            _context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val connectionInfo = connectivityManager.activeNetworkInfo
        return connectionInfo != null && connectionInfo.isConnected
    }
}