package com.picpay.desafio.android.framework.usecase

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.picpay.desafio.android.presentation.users.User
import kotlinx.coroutines.flow.Flow

interface UserUseCase {

    operator fun invoke(params: GetUsersParams): Flow<PagingData<User>>

    data class GetUsersParams(val query: String, val pagingConfig: PagingConfig)
}