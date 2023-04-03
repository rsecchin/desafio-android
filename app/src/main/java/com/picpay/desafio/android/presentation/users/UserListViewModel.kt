package com.picpay.desafio.android.presentation.users

import androidx.lifecycle.*
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.picpay.desafio.android.framework.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val useCase: UserUseCase
) : ViewModel() {

    private val action = MutableLiveData<Action>()
    val state: LiveData<UiState> = action
        .distinctUntilChanged()
        .switchMap { action ->
            when (action) {
                is Action.GetAll -> {
                    useCase(
                        UserUseCase.GetUsersParams(getPagingConfig())
                    ).cachedIn(viewModelScope).map {
                        UiState.SearchResult(it)
                    }.asLiveData(Dispatchers.Main)
                }
            }
        }

    fun usersPagingData(): Flow<PagingData<User>> {
        return useCase(
            UserUseCase.GetUsersParams(getPagingConfig())
        ).cachedIn(viewModelScope)
    }

    private fun getPagingConfig() = PagingConfig(
        pageSize = 20
    )

    fun getUsers() {
        action.value = Action.GetAll
    }

    sealed class UiState {
        data class SearchResult(val data: PagingData<User>) : UiState()
    }

    sealed class Action {
        object GetAll : Action()
    }
}