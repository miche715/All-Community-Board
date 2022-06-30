package com.example.client.user.service

import com.example.client.user.domain.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserRetrofitService
{
    @POST("sign-up")
    suspend fun signUp(@Body user: User): SignUpResponse

    @GET("sign-in")
    suspend fun signIn(@Query("username") username: String, @Query("password") password: String): SignInResponse

    @GET("find-username")
    suspend fun findUsername(@Query("name") name: String, @Query("email") email: String): FindUsernameResponse

    @GET("find-password")
    suspend fun findPassword(@Query("name") name: String, @Query("username") username: String): FindPasswordResponse
}