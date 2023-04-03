package com.picpay.desafio.android.framework.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.picpay.desafio.android.presentation.users.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository {

    fun getCachedUsers(
        pagingConfig: PagingConfig
    ): Flow<PagingData<User>>
}