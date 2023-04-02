package com.picpay.desafio.android.framework.usecase

import androidx.paging.PagingData
import com.picpay.desafio.android.framework.repository.UsersRepository
import com.picpay.desafio.android.presentation.users.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserUseCaseImpl @Inject constructor(
    private val charactersRepository: UsersRepository
) : PagingUseCase<UserUseCase.GetUsersParams, User>(), UserUseCase {

    override fun createFlowObservable(params: UserUseCase.GetUsersParams): Flow<PagingData<User>> {
        return charactersRepository.getCachedUsers(params.query, params.pagingConfig)
    }
}