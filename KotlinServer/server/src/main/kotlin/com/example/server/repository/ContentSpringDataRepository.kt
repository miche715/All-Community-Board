package com.example.server.repository

import com.example.server.domain.Content
import org.springframework.data.jpa.repository.JpaRepository

interface ContentSpringDataRepository : JpaRepository<Content, Long>, ContentRepository
{
}