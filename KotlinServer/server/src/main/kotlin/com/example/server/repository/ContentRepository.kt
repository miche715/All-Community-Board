package com.example.server.repository

import com.example.server.domain.Content

interface ContentRepository
{
    fun save(content: Content): Content

    fun findById(id: Long): Content

    fun findAllByOrderByContentIdDesc(): MutableList<Content>

    fun delete(content: Content)
}