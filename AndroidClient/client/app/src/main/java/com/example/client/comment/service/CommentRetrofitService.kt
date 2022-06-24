package com.example.client.comment.service

import com.example.client.comment.domain.Comment
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface CommentRetrofitService
{
    @POST("create")
    fun addComment(@Body comment: Comment, @Query(value = "content_id") contentId: Long, @Query(value = "user_id") userId: Long): Call<Comment>
}