package com.picpay.desafio.android.framework.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.picpay.desafio.android.framework.db.AppDatabase
import com.picpay.desafio.android.framework.db.entity.RemoteKey
import com.picpay.desafio.android.framework.db.entity.UserEntity
import com.picpay.desafio.android.framework.repository.remote.UserRemoteDataSource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class UsersRemoteMediator @Inject constructor(
    private val database: AppDatabase,
    private val remoteDataSource: UserRemoteDataSource
) : RemoteMediator<Int, UserEntity>() {

    private val characterDao = database.userDao()
    private val remoteKeyDao = database.remoteKeyDao()

    @Suppress("ReturnCount")
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserEntity>
    ): MediatorResult {
        return try {

            val characterPaging = remoteDataSource.getUsers()
            val responseOffset = 20
            val totalCharacters = characterPaging.size


            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeyDao.clearAll()
                    characterDao.deleteAllUsers()
                }

                remoteKeyDao.insertOrReplace(
                    RemoteKey(nextOffset = responseOffset + state.config.pageSize)
                )

                val charactersEntities = characterPaging.map {
                    UserEntity(id = it.id, name = it.name, img = it.img, username = it.username)
                }

                characterDao.insertUsers(charactersEntities)
            }

            MediatorResult.Success(endOfPaginationReached = responseOffset >= totalCharacters)

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

}