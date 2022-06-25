package com.example.server.service

import com.example.server.domain.Comment
import com.example.server.domain.Content
import com.example.server.repository.CommentRepository
import com.example.server.repository.ContentRepository
import com.example.server.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Transactional
class CommentService(@Autowired private val commentRepository: CommentRepository,
                     @Autowired private val contentRepository: ContentRepository,
                     @Autowired private val userRepository: UserRepository
)
{
    fun addComment(comment: Comment, contentId: Long, userId: Long): Content
    {
        commentRepository.save(comment.apply()
        {
            this.content = contentRepository.findById(contentId)
            this.user = userRepository.findById(userId)
        }).run()
        {
            return contentRepository.findById(contentId).let()
            {
                it.comments.add(this)
                it.commentNum = it.comments.size
                contentRepository.save(it)
            }
        }
    }

    fun getAll(contentId: Long): MutableList<Comment>
    {
        return commentRepository.findByContent(contentRepository.findById(contentId))
    }

    fun modifyComment(comment: Comment): Comment
    {
        return commentRepository.save(comment)
    }

    fun removeComment(commentId: Long)
    {
        commentRepository.findById(commentId).run()
        {
            contentRepository.findById(this.content?.contentId!!).apply()
            {
                this.commentNum = this.commentNum!! - 1
                contentRepository.save(this)
            }

            commentRepository.delete(this)
        }
    }
}