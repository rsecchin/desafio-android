package com.picpay.desafio.android.framework.repository

import androidx.room.withTransaction
import com.picpay.desafio.android.framework.db.AppDatabase
import com.picpay.desafio.android.framework.db.entity.UserEntity
import com.picpay.desafio.android.framework.db.entity.toUsersModel
import com.picpay.desafio.android.framework.repository.remote.UserRemoteDataSource
import com.picpay.desafio.android.framework.usecase.ResultStatus
import com.picpay.desafio.android.framework.usecase.networkBoundResource
import com.picpay.desafio.android.presentation.users.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource,
    private val userDb: AppDatabase
) : UsersRepository {

    private val restaurantDao = userDb.userDao()

    override suspend fun getUsers(): Flow<ResultStatus<List<User>>> = networkBoundResource(
        query = {
            restaurantDao.getAllUsers().map {
                it.toUsersModel()
            }
        },
        fetch = {
            delay(2000)
            remoteDataSource.getUsers()
        },
        saveFetchResult = { users ->
            userDb.withTransaction {
                restaurantDao.deleteAllUsers()
                restaurantDao.insertUsers(users.map {
                    UserEntity(img = it.img, name = it.name, id = it.id, username = it.username)
                })
            }
        }
    )
}