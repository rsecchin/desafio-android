package com.picpay.desafio.android.framework.repository.remote

import com.picpay.desafio.android.framework.model.UserResponse

interface UserRemoteDataSource {

    suspend fun getUsers() : List<UserResponse>
}