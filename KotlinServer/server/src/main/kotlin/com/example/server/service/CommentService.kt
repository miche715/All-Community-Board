package com.example.server.service

import com.example.server.repository.CommentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Transactional
class CommentService(@Autowired private val commentRepository: CommentRepository)
{

}