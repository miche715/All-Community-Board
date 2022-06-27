package com.example.server.repository

import com.example.server.domain.Content
import org.springframework.data.domain.Pageable

interface ContentRepository
{
    fun save(content: Content): Content

    fun findById(id: Long): Content

    fun findAllByOrderByContentIdDesc(pageable: Pageable): MutableList<Content>

    fun findAllByTitleContaining(keyword: String, pageable: Pageable): MutableList<Content>

    fun delete(content: Content)
}