package com.picpay.desafio.android.framework.repository

import com.picpay.desafio.android.framework.repository.remote.UserRemoteDataSource
import com.picpay.desafio.android.framework.usecase.ResultStatus
import com.picpay.desafio.android.presentation.users.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource
) : UsersRepository {

    override suspend fun getUsers(): Flow<ResultStatus<List<User>>> = flow {
        emit(ResultStatus.Loading)
        emit(
            ResultStatus.Success(
                remoteDataSource.getUsers().map { User(it.img, it.name, it.id, it.username) })
        )
    }.catch { throwable ->
        emit(ResultStatus.Error(throwable))
    }
}