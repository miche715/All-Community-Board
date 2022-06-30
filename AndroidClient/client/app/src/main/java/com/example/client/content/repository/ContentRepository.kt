package com.example.client.content.repository

import com.example.client.content.domain.Content
import com.example.client.content.service.ContentRetrofitServiceObject

class ContentRepository
{
    private val contentRetrofitService = ContentRetrofitServiceObject.getRetrofitInstance()

    suspend fun modifyContentCheck(content: Content, userId: Long) = contentRetrofitService.modifyContent(content, userId)

    suspend fun removeContentCheck(contentId: Long) = contentRetrofitService.removeContent(contentId)
}