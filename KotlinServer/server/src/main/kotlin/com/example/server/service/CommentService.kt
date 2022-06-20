package com.example.server.service

import com.example.server.domain.Comment
import com.example.server.domain.Content
import com.example.server.repository.CommentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Transactional
class CommentService(@Autowired private val commentRepository: CommentRepository)
{
    fun addComment(comment: Comment): Comment
    {
        return commentRepository.save(comment)
    }

    fun getAll(content: Content): MutableList<Comment>
    {
        return commentRepository.findByContentId(content)
    }

    fun modifyComment(comment: Comment): Comment
    {
        return commentRepository.save(comment)
    }

    fun removeComment(commentId: Long)
    {
        return commentRepository.delete(commentRepository.findById(commentId))
    }
}