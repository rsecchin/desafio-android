package com.picpay.desafio.android.presentation.users

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.isA
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.framework.repository.UsersRepository
import com.picpay.desafio.android.framework.usecase.ResultStatus
import com.picpay.desafio.android.presentation.MainCoroutineRule
import junit.framework.Assert
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class UserListViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutine = MainCoroutineRule()

    @Mock
    lateinit var usersRepository: UsersRepository

    @Mock
    private lateinit var uiStateObserver: Observer<UserListViewModel.UiState>

    private val users = listOf(User("", "", 1, ""))

    private lateinit var userListViewModel: UserListViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        userListViewModel = UserListViewModel(usersRepository)
        userListViewModel.uiState.observeForever(uiStateObserver)
    }

    @Test
    fun `should notify uiState with Success from UiState when get weather returns success`() =
        runTest {
            // Arrange
            whenever(usersRepository.getUsers())
                .thenReturn(
                    flowOf(
                        ResultStatus.Success(
                            users
                        )
                    )
                )

            // Act
            userListViewModel.getUsers()

            // Assert
            Mockito.verify(uiStateObserver).onChanged(isA<UserListViewModel.UiState.Success>())

            val uiStateSuccess =
                userListViewModel.uiState.value as UserListViewModel.UiState.Success
            val weatherTest = uiStateSuccess.userList

            Assert.assertEquals(
                1,
                weatherTest.size
            )
        }

    @Test
    fun `should notify uiState with Error from UiState when get weather returns an exception`() =
        runTest {
            // Arrange
            whenever(usersRepository.getUsers())
                .thenReturn(
                    flowOf(
                        ResultStatus.Error(Throwable())
                    )
                )

            // Act
            userListViewModel.getUsers()

            // Assert
            Mockito.verify(uiStateObserver).onChanged(isA<UserListViewModel.UiState.Error>())
        }

}