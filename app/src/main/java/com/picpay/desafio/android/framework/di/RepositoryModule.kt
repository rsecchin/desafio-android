package com.picpay.desafio.android.framework.di

import com.picpay.desafio.android.framework.repository.remote.UserRemoteDataSource
import com.picpay.desafio.android.framework.repository.remote.UserRetrofitRemoteDataSource
import com.picpay.desafio.android.framework.repository.UsersRepository
import com.picpay.desafio.android.framework.repository.UsersRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindUserRepository(repository: UsersRepositoryImpl): UsersRepository

    @Binds
    fun bindUserRemoteDataSource(remoteDataSource: UserRetrofitRemoteDataSource): UserRemoteDataSource
}