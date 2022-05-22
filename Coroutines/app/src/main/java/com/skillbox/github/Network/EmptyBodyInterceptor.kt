package com.skillbox.github.Network

import com.skillbox.github.data.StatusCode
import okhttp3.Interceptor
import okhttp3.Response

class EmptyBodyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        return if (response.code == StatusCode.SUCCESSFUL.statusCode)
            response
                .newBuilder()
                .code(200)
                .build()
        else response
    }
}