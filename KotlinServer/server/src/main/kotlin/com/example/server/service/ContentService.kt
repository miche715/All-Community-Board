package com.example.server.service

import com.example.server.domain.Content
import com.example.server.repository.ContentRepository
import com.example.server.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.data.domain.Pageable

@Transactional
class ContentService(@Autowired private val contentRepository: ContentRepository, @Autowired private val userRepository: UserRepository)
{
    fun addContent(content: Content, userId: Long): Boolean
    {
        return content.apply()
        {
            this.user = userRepository.findById(userId)
        }.run()
        {
            contentRepository.save(this)
            true
        }
    }

    fun getAll(pageable: Pageable): MutableList<Content>
    {
        return contentRepository.findAllByOrderByContentIdDesc(pageable)
    }

    fun modifyContent(content: Content, userId: Long): Content
    {
        content.apply()
        {
            this.user = userRepository.findById(userId)
        }.run()
        {
            return contentRepository.save(this)
        }
    }

    fun removeContent(contentId: Long)
    {
        return contentRepository.delete(contentRepository.findById(contentId))
    }
}