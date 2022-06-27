package com.example.server.repository

import com.example.server.domain.Content
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ContentSpringDataRepository : JpaRepository<Content, Long>, ContentRepository
{
    override fun findAllByOrderByContentIdDesc(pageable: Pageable): MutableList<Content>

    override fun findAllByTitleContaining(keyword: String, pageable: Pageable): MutableList<Content>
}