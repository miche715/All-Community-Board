package com.example.client.user.repository

import com.example.client.user.domain.SignInRequest
import com.example.client.user.service.UserRetrofitServiceObject

class UserRepository
{
    private val userRetrofitService = UserRetrofitServiceObject.getRetrofitInstance()

    suspend fun signInCheck(request: SignInRequest) = userRetrofitService.signIn(request.username!!, request.password!!)
}