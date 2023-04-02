package com.picpay.desafio.android.presentation.users

import androidx.paging.PagingData
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.framework.usecase.UserUseCase
import com.picpay.desafio.android.presentation.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
internal class UserListViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var userUseCase: UserUseCase

    private lateinit var userListViewModel: UserListViewModel

    private val pagingDataUsers = PagingData.from(
        listOf(User("", "", 1, ""))
    )

    @Before
    fun setUp() {
        userListViewModel =
            UserListViewModel(userUseCase)
    }

    @Test
    fun `should validate the paging data object values when calling usersPagingData`() =
        runTest {

            whenever(userUseCase.invoke(any())).thenReturn(
                flowOf(pagingDataUsers)
            )

            val result = userListViewModel.usersPagingData()

            assertNotNull(result.first())
        }

    @Test(expected = RuntimeException::class)
    fun `should throw an exception when the calling to the use case returns an exception`() {
        runTest {
            whenever(userUseCase.invoke(any())).thenThrow(RuntimeException())

            userListViewModel.usersPagingData()
        }
    }

}