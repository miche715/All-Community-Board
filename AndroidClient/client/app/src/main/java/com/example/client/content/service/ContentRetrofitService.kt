package com.example.client.content.service

import com.example.client.content.domain.Content
import retrofit2.Call
import retrofit2.http.*

interface ContentRetrofitService
{
    @POST("create")
    fun addContent(@Body content: Content, @Query(value = "user_id") userId: Long): Call<Boolean>

    @GET("all")
    fun getAll(@Query(value = "page") page: Int, @Query(value = "size") size: Int): Call<MutableList<Content>>

    @GET("search")
    fun getSearch(@Query(value = "keyword") keyword: String, @Query(value = "page") page: Int, @Query(value = "size") size: Int): Call<MutableList<Content>>

    @PUT("update")
    fun modifyContent(@Body content: Content, @Query(value = "user_id") userId: Long): Call<Content>

    @DELETE("delete")
    fun removeContent(@Query(value = "content_id") contentId: Long): Call<Boolean>
}