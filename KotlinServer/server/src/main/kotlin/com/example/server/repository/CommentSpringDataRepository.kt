package com.example.server.repository

import com.example.server.domain.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentSpringDataRepository : JpaRepository<Comment, Long>, CommentRepository
{
}