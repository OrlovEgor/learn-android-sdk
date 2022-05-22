package com.skillbox.github.Network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

object Networking {

    private val okHttpclient = OkHttpClient.Builder()
        .addNetworkInterceptor(EmptyBodyInterceptor())
        .addNetworkInterceptor(
            TokenInterceptor(
                "Authorization",
                "token ${AuthToken.getToken()}"
            )
        )
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpclient)
        .build()

    val githubApi: GithubApi
        get() = retrofit.create()
}