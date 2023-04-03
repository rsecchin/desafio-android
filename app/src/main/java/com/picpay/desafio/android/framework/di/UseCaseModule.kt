package com.picpay.desafio.android.framework.di

import com.picpay.desafio.android.framework.usecase.UserUseCase
import com.picpay.desafio.android.framework.usecase.UserUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindGetUserUseCase( useCase: UserUseCaseImpl) : UserUseCase
}