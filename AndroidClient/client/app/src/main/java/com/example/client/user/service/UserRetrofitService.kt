package com.example.client.user.service

import com.example.client.user.domain.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserRetrofitService
{
    @GET("sign-in")
    fun signIn(@Query("username") username: String, @Query("password") password: String): Call<User?>
}