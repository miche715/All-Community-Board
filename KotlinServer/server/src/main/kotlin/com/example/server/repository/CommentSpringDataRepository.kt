package com.example.server.repository

import com.example.server.domain.Comment
import com.example.server.domain.Content
import org.springframework.data.jpa.repository.JpaRepository

interface CommentSpringDataRepository : JpaRepository<Comment, Long>, CommentRepository
{
    override fun findByContent(content: Content): MutableList<Comment>
}