package ru.orlovegor.moviesearchapp.networking

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import ru.orlovegor.moviesearchapp.adapters.MovieParseAdapter

object Network {

    private const val BASE_URL = "http://www.omdbapi.com/"
    private const val queryName = "apikey"
    private const val API_KEY = "f358c57e"

    private val okHttpClient = OkHttpClient.Builder()
       .addNetworkInterceptor(ApiKeyHeader(queryName, API_KEY))
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private val moshiAdapter = Moshi.Builder().add(MovieParseAdapter()).build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshiAdapter))
        .client(okHttpClient)
        .build()

    val movieApi: MovieApi
        get() = retrofit.create()

}