package com.example.files.network

import android.content.Context
import com.example.files.MyApplication
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create

object Networking {

    private val _context: Context by lazy { MyApplication.context }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(ConnectionInterceptor(_context))
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


