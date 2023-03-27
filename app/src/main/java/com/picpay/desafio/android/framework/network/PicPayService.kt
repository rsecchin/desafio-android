package com.picpay.desafio.android.framework.network

import com.picpay.desafio.android.framework.model.UserResponse
import com.picpay.desafio.android.presentation.users.User
import retrofit2.Call
import retrofit2.http.GET


interface PicPayService {

    @GET("users")
    suspend fun getUsers(): List<UserResponse>
}