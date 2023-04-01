package com.picpay.desafio.android.presentation.users

import androidx.lifecycle.*
import com.picpay.desafio.android.framework.repository.UsersRepository
import com.picpay.desafio.android.framework.usecase.ResultStatus
import com.picpay.desafio.android.presentation.users.adapter.UserItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val repository: UsersRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    fun getUsers() = viewModelScope.launch {
        repository.getUsers().watchStatus()
    }

    private fun Flow<ResultStatus<List<User>>>.watchStatus() = viewModelScope.launch {
        collect { status ->
            _uiState.value = when (status) {
                ResultStatus.Loading -> UiState.Loading
                is ResultStatus.Success -> {
                    UiState.Success(status.data.map {
                        UserItem(
                            it.img,
                            it.name,
                            it.id,
                            it.username
                        )
                    })
                }
                is ResultStatus.Error -> UiState.Error
            }
        }
    }

    sealed class UiState {
        object Loading : UiState()
        data class Success(val userList: List<UserItem>) : UiState()
        object Error : UiState()
    }
}