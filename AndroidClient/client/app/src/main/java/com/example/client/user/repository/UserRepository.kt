package com.example.client.user.repository

import com.example.client.user.service.UserRetrofitServiceObject

class UserRepository
{
    private val userRetrofitService = UserRetrofitServiceObject.getRetrofitInstance()

    //suspend fun signUpCheck(request: SignUpRequest) = userRetrofitService.signUp(request.user!!)

    suspend fun signInCheck(username: String, password: String) = userRetrofitService.signIn(username, password)
}