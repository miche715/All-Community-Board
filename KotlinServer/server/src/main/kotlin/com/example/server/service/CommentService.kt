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

    fun removeComment(commentId: Long): Content
    {
        commentRepository.findById(commentId).run()
        {
            commentRepository.delete(this)
            return contentRepository.findById(this.content!!.contentId!!).let()
            {
                it.comments.remove(this)
                it.commentNum = it.comments.size
                it
            }
        }
    }
}