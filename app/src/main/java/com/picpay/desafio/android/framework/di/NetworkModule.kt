package com.picpay.desafio.android.framework.di

import com.picpay.desafio.android.framework.network.PicPayService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory,
    ): PicPayService {
        return Retrofit.Builder()
            .baseUrl("https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/")
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
            .create(PicPayService::class.java)
    }
}