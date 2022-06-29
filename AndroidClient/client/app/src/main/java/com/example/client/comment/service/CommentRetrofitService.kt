package com.example.client.comment.service

import com.example.client.comment.domain.AddCommentResponse
import com.example.client.comment.domain.Comment
import com.example.client.comment.domain.RemoveCommentResponse
import retrofit2.Call
import retrofit2.http.*

interface CommentRetrofitService
{
    @POST("create")
    suspend fun addComment(@Body comment: Comment, @Query(value = "content_id") contentId: Long, @Query(value = "user_id") userId: Long): AddCommentResponse

    @GET("all")
    fun getAll(@Query(value = "content_id") contentId: Long): Call<MutableList<Comment>>

    @DELETE("delete")
    suspend fun removeComment(@Query(value = "comment_id") commentId: Long): RemoveCommentResponse
}