package com.example.client.user.repository

import com.example.client.user.domain.User
import com.example.client.user.service.UserRetrofitServiceObject

class UserRepository
{
    private val userRetrofitService = UserRetrofitServiceObject.getRetrofitInstance()

    suspend fun signUpCheck(user: User) = userRetrofitService.signUp(user)

    suspend fun signInCheck(username: String, password: String) = userRetrofitService.signIn(username, password)

    suspend fun findUsernameCheck(name: String, email: String) = userRetrofitService.findUsername(name, email)
}