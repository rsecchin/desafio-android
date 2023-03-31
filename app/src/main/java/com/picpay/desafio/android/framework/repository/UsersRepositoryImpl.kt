package com.picpay.desafio.android.framework.repository

import com.picpay.desafio.android.framework.db.AppDatabase
import com.picpay.desafio.android.framework.db.entity.UserEntity
import com.picpay.desafio.android.framework.db.entity.toUsersModel
import com.picpay.desafio.android.framework.repository.remote.UserRemoteDataSource
import com.picpay.desafio.android.framework.usecase.ResultStatus
import com.picpay.desafio.android.presentation.users.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource,
    private val userDb: AppDatabase
) : UsersRepository {

    override suspend fun getUsers(): Flow<ResultStatus<List<User>>> = flow {
        emit(ResultStatus.Loading)
        userDb.userDao().insertUsers(remoteDataSource.getUsers().map {
            UserEntity(img = it.img, name = it.name, id = it.id, username = it.username)
        })
        emit(
            ResultStatus.Success(
                remoteDataSource.getUsers().map { User(it.img, it.name, it.id, it.username) })
        )
    }.catch { throwable ->
        emit(ResultStatus.Error(throwable))
    }

    override suspend fun getUsersDb(): Flow<List<User>> {
        return userDb.userDao().getAllUsers().map {
            it.toUsersModel()
        }
    }
}