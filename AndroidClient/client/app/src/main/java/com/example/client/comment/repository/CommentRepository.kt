package com.example.client.comment.repository

import com.example.client.comment.domain.Comment
import com.example.client.comment.service.CommentRetrofitServiceObject

class CommentRepository
{
    private val commentRetrofitService = CommentRetrofitServiceObject.getRetrofitInstance()

    suspend fun addCommentCheck(comment: Comment, contentId: Long, userId: Long) = commentRetrofitService.addComment(comment, contentId, userId)

    suspend fun removeCommentCheck(commentId: Long) = commentRetrofitService.removeComment(commentId)
}