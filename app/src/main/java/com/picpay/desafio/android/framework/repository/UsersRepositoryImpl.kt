package com.picpay.desafio.android.framework.repository

import androidx.paging.*
import com.picpay.desafio.android.framework.db.AppDatabase
import com.picpay.desafio.android.framework.paging.UsersPagingSource
import com.picpay.desafio.android.framework.paging.UsersRemoteMediator
import com.picpay.desafio.android.framework.repository.remote.UserRemoteDataSource
import com.picpay.desafio.android.presentation.users.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource,
    private val userDb: AppDatabase
) : UsersRepository {

    override fun getUsers(query: String): PagingSource<Int, User> {
        return UsersPagingSource(remoteDataSource, query)
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getCachedUsers(
        query: String,
        pagingConfig: PagingConfig
    ): Flow<PagingData<User>> {
        return Pager(
            config = pagingConfig,
            remoteMediator = UsersRemoteMediator(userDb, remoteDataSource)
        ) { userDb.userDao().getAllUsers() }.flow.map { pagingData ->
            pagingData.map {
                User(it.img, it.name, it.id, it.username)
            }
        }
    }
}