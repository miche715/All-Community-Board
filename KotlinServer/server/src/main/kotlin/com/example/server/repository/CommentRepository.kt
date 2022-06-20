package com.example.server.repository

import com.example.server.domain.Comment
import com.example.server.domain.Content

interface CommentRepository
{
    fun save(comment: Comment): Comment

    fun findById(id: Long): Comment

    fun findByContentId(content: Content): MutableList<Comment>

    fun delete(comment: Comment)
}