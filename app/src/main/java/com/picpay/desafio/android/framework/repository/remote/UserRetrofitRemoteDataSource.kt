package com.picpay.desafio.android.framework.repository.remote

import com.picpay.desafio.android.framework.model.UserResponse
import com.picpay.desafio.android.framework.network.PicPayService
import javax.inject.Inject

class UserRetrofitRemoteDataSource @Inject constructor(
    private val picPayService: PicPayService
) : UserRemoteDataSource {

    override suspend fun getUsers(): List<UserResponse> {
        return picPayService.getUsers()
    }
}