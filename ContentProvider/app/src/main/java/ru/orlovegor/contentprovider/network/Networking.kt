package ru.orlovegor.contentprovider.network

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import ru.orlovegor.contentprovider.App

object Networking {

    private val context: Context by lazy { App.context }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(ConnectionInterceptor(context))
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
