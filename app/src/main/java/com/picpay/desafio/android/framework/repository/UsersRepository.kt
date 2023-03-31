package com.picpay.desafio.android.framework.repository

import com.picpay.desafio.android.framework.usecase.ResultStatus
import com.picpay.desafio.android.presentation.users.User
import com.picpay.desafio.android.presentation.users.adapter.UserItem
import kotlinx.coroutines.flow.Flow

interface UsersRepository {

    suspend fun getUsers(): Flow<ResultStatus<List<User>>>
    suspend fun getUsersDb():  Flow<List<User>>
}