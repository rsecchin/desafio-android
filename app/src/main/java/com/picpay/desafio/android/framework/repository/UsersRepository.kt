package com.picpay.desafio.android.framework.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.picpay.desafio.android.presentation.users.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository {

    fun getUsers(query: String): PagingSource<Int, User>

    fun getCachedUsers(
        query: String,
        pagingConfig: PagingConfig
    ): Flow<PagingData<User>>
}