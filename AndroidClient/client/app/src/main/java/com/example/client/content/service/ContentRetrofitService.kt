package com.example.client.content.service

import com.example.client.content.domain.*
import retrofit2.Call
import retrofit2.http.*

interface ContentRetrofitService
{
    @POST("create")
    suspend fun addContent(@Body content: Content, @Query(value = "user_id") userId: Long): AddContentResponse

    @GET("all")
    suspend fun getAll(@Query(value = "page") page: Int, @Query(value = "size") size: Int): GetAllResponse

    @GET("search")
    fun getSearch(@Query(value = "keyword") keyword: String, @Query(value = "page") page: Int, @Query(value = "size") size: Int): Call<MutableList<Content>>

    @PUT("update")
    suspend fun modifyContent(@Body content: Content, @Query(value = "user_id") userId: Long): ModifyContentResponse

    @DELETE("delete")
    suspend fun removeContent(@Query(value = "content_id") contentId: Long): RemoveContentResponse
}