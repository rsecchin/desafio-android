package com.picpay.desafio.android.framework.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.picpay.desafio.android.framework.repository.remote.UserRemoteDataSource
import com.picpay.desafio.android.presentation.users.User

class UsersPagingSource(
    private val remoteDataSource: UserRemoteDataSource,
    private val query: String
) : PagingSource<Int, User>() {

    @Suppress("TooGenericExceptionCaught")
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val offset = params.key ?: 0

            val queries = hashMapOf(
                "offset" to offset.toString()
            )

            if (query.isNotEmpty()){
                queries["nameStartsWith"] = query
            }

            val characterPaging = remoteDataSource.getUsers()

            val responseOffset = 20
            val totalCharacters = characterPaging.size

            LoadResult.Page(

                data = characterPaging.map { User(it.img, it.name, it.id, it.username) },
                prevKey = null,
                nextKey = if (responseOffset < totalCharacters) {
                    responseOffset + LIMIT
                } else null
            )

        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(LIMIT) ?: anchorPage?.nextKey?.minus(LIMIT)
        }
    }

    companion object {
        private const val LIMIT = 20
    }
}