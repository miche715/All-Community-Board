package com.example.client.user.service

import com.example.client.user.domain.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserRetrofitService
{
    @POST("sign-up")
    fun signUp(@Body user: User): Call<Boolean>

    @GET("sign-in")
    fun signIn(@Query("username") username: String, @Query("password") password: String): Call<User?>
}