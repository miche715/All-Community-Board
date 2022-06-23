package com.example.client.content.service

import com.example.client.content.domain.Content
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ContentRetrofitService
{
    @POST("create")
    fun addContent(@Body content: Content, @Query(value = "user_id") userId: Long): Call<Content>

    @GET("all")
    fun getAll(): Call<MutableList<Content>>
}