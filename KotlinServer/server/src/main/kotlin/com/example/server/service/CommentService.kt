package com.example.server.service

import com.example.server.domain.Comment
import com.example.server.domain.Content
import com.example.server.repository.CommentRepository
import com.example.server.repository.ContentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Transactional
class CommentService(@Autowired private val commentRepository: CommentRepository, @Autowired private val contentRepository: ContentRepository)
{
    fun addComment(comment: Comment): Comment
    {
        return commentRepository.save(comment).run()
        {
            contentRepository.findById(comment.content?.contentId!!).comments.add(this)
            this
        }
    }

    fun getAll(content: Content): MutableList<Comment>
    {
        return commentRepository.findByContent(content)
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