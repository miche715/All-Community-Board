package com.example.server.configuration

import com.example.server.repository.CommentRepository
import com.example.server.repository.ContentRepository
import com.example.server.repository.UserRepository
import com.example.server.service.CommentService
import com.example.server.service.ContentService
import com.example.server.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationConfiguration(@Autowired private val userRepository: UserRepository,
                               @Autowired private val contentRepository: ContentRepository,
                               @Autowired private val commentRepository: CommentRepository)
{
    @Bean
    fun userService(): UserService
    {
        return UserService(userRepository)
    }

    @Bean
    fun contentService(): ContentService
    {
        return ContentService(contentRepository)
    }

    @Bean
    fun commentService(): CommentService
    {
        return CommentService(commentRepository, contentRepository)
    }
}