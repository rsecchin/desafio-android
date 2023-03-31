package com.picpay.desafio.android.presentation.users

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.picpay.desafio.android.R
import com.picpay.desafio.android.extension.asJsonString
import com.picpay.desafio.android.framework.di.BaseUrlModule
import com.picpay.desafio.android.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@UninstallModules(BaseUrlModule::class)
@HiltAndroidTest
class UserListFragmentTest{

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var server : MockWebServer

    @Before
    fun setUp(){
        server = MockWebServer().apply {
            start(8080)
        }
        launchFragmentInHiltContainer<UserListFragment>()
    }

    @Test
    fun shouldShowUsers_whenViewIsCreated(){
        server.enqueue(MockResponse().setBody("users_p1.json".asJsonString()))

        onView(
            withId(R.id.recycler_users)
        ).check(
            matches(isDisplayed())
        )
    }

    @Test
    fun shouldShowErrorViewWhenReceivesErrorFromApi() {
        server.enqueue(MockResponse().setResponseCode(404))

        onView(
            withId(R.id.text_initial_loading_error)
        ).check(
            matches(isDisplayed())
        )
    }

    @After
    fun teardown() {
        server.shutdown()
    }
}