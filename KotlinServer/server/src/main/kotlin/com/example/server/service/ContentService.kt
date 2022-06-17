package com.example.server.service

import com.example.server.repository.ContentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Transactional
class ContentService(@Autowired private val contentRepository: ContentRepository)
{

}