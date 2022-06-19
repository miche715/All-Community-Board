package com.example.server.service

import com.example.server.domain.Content
import com.example.server.repository.ContentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Transactional
class ContentService(@Autowired private val contentRepository: ContentRepository)
{
    fun addContent(content: Content): Content
    {
        return contentRepository.save(content)
    }

    fun getContent(contentId: Long): Content
    {
        return contentRepository.findById(contentId)
    }

    fun getAll(): MutableList<Content>
    {
        return contentRepository.findAll()
    }

    fun modifyContent(content: Content): Content
    {
        return contentRepository.save(content)
    }

    fun removeContent(contentId: Long)
    {
        return contentRepository.delete(contentRepository.findById(contentId))
    }
}