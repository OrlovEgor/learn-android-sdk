package ru.skillbox.dependency_injection.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import ru.skillbox.dependency_injection.data.Api
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkhttp() = OkHttpClient.Builder()
        .addNetworkInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .followRedirects(true)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okhttp: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://google.com")
        .client(okhttp)
        .build()

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit) = retrofit.create(Api::class.java)
}