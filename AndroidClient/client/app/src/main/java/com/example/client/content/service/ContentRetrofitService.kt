package com.example.client.content.service

import com.example.client.content.domain.Content
import retrofit2.Call
import retrofit2.http.*

interface ContentRetrofitService
{
    @POST("create")
    fun addContent(@Body content: Content, @Query(value = "user_id") userId: Long): Call<Content>

    @GET("all")
    fun getAll(): Call<MutableList<Content>>

    @DELETE("delete")
    fun removeContent(@Query(value = "content_id") contentId: Long): Call<Boolean>
}