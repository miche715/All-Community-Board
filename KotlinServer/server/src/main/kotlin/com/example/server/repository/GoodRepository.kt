package com.example.server.repository

import com.example.server.domain.Content
import com.example.server.domain.Good
import com.example.server.domain.User

interface GoodRepository
{
    fun save(good: Good): Good

    fun findByContentAndUser(content: Content, user: User): Good?
}