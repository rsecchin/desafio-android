package com.picpay.desafio.android.framework.di

import com.picpay.desafio.android.framework.di.qualifer.BaseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object BaseUrlModule {

    @BaseUrl
    @Provides
    fun provideBaseUrl(): String = "https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/"
}