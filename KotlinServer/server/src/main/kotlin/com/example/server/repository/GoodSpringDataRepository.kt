package com.example.server.repository

import com.example.server.domain.Content
import com.example.server.domain.Good
import com.example.server.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface GoodSpringDataRepository : JpaRepository<Good, Long>, GoodRepository
{
    override fun findByContentAndUser(content: Content, user: User): Good?
}