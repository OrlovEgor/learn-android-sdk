package ru.skillbox.dependency_injection.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import ru.skillbox.dependency_injection.data.Api
import ru.skillbox.dependency_injection.data.AppVersionInterceptor
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkhttp(
        @LoggingInterceptorOkHttpClient loggingInterceptor: Interceptor,
        @AppVersionInterceptorOkHttpClient appVersionInterceptor: Interceptor
    ) = OkHttpClient.Builder()
        .addNetworkInterceptor(loggingInterceptor)
        .addInterceptor(appVersionInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okhttp: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl("https://google.com")
        .client(okhttp)
        .build()

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)

    @LoggingInterceptorOkHttpClient
    @Provides
    @Singleton
    fun provideLoggingInterceptorOkHttpClient(
    ): Interceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @AppVersionInterceptorOkHttpClient
    @Provides
    @Singleton
    fun provideAppVersionInterceptorOkHttpClient(): Interceptor {
        return AppVersionInterceptor()
    }

}

