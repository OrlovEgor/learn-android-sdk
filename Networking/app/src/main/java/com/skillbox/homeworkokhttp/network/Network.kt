package com.skillbox.homeworkokhttp.network


import android.widget.AdapterViewFlipper
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor


object Network {
    val flipperNetworkPlugin = NetworkFlipperPlugin()

    private const val MOVIE_API_KEY = "f358c57e"

    private val client = OkHttpClient.Builder()

        .addNetworkInterceptor(CustomHeader("apikey", MOVIE_API_KEY))
        .addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })
        .addNetworkInterceptor( FlipperOkhttpInterceptor(flipperNetworkPlugin))
        .build()

    fun getSearchMovieCall(tittle: String, year: String, genre: String): Call {
        val url = HttpUrl.Builder()
            .scheme("http")
            .host("www.omdbapi.com")
            .addQueryParameter("s", tittle)
            .addQueryParameter("type", genre)
            .addQueryParameter("y", year)
            .build()

        val request = Request.Builder()
            .get()
            .url(url)
            .build()
        return client.newCall(request)
    }
}