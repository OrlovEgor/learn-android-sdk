package ru.orlovegor.material.networking

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyHeader(
    private val queryName: String,
    private val apiKeyHeader: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalUrl = chain.request().url
        val modifiedUrl = originalUrl.newBuilder().addQueryParameter(
            queryName,
            apiKeyHeader
        ).build()
        val modifiedRequest = chain.request().newBuilder()
            .url(modifiedUrl)
            .build()
        return chain.proceed(modifiedRequest)
    }
}