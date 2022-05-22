package com.skillbox.github.Network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(
    private val queryName: String,
    private val queryHeader: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        return if (AuthToken.getToken().isEmpty()) {
            chain.proceed(originalRequest)
        } else {
            Log.d("INTERCEPT", " Interceptor success")
            chain.proceed(
                originalRequest.newBuilder()
                    .addHeader(queryName, queryHeader)
                    .build()
            )
        }
    }
}