package com.clicks.newsapp.di

import android.content.Context
import com.clicks.newsapp.BuildConfig
import com.clicks.newsapp.data.remote.Api
import com.clicks.newsapp.utils.Constants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun getRetrofit(
        client: OkHttpClient,

        ): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    @Provides
    @Singleton
    fun providesApiInterface(retrofit: Retrofit): Api = retrofit.create(
        Api::class.java
    )

    @Provides
    @Singleton
    fun getOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        @ApplicationContext context: Context
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(50, TimeUnit.SECONDS)
            .readTimeout(50, TimeUnit.SECONDS)
            .writeTimeout(50, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor { chain -> buildHeaders(chain, context) }
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
//        logging.level = HttpLoggingInterceptor.Level.HEADERS
        logging.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return logging
    }

    private fun buildHeaders(chain: Interceptor.Chain, context: Context): Response {
        val builder = chain.request().newBuilder()
            .addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/json")

        return chain.proceed(builder.build())
    }

}