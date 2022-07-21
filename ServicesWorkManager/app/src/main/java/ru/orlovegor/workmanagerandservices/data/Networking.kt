package ru.orlovegor.workmanagerandservices.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create

object Networking {

        private val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .followRedirects(true)
            .build()

        private val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://google.com")
            .build()

        val downloadApi: DownloadApi
            get() = retrofit.create()
    }