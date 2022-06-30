package com.example.client.content.repository

import com.example.client.content.domain.Content
import com.example.client.content.service.ContentRetrofitServiceObject

class ContentRepository
{
    private val contentRetrofitService = ContentRetrofitServiceObject.getRetrofitInstance()

    suspend fun addContentCheck(content: Content, userId: Long) = contentRetrofitService.addContent(content, userId)

    suspend fun getAllCheck(page: Int, size: Int) = contentRetrofitService.getAll(page, size)

    suspend fun getSearchCheck(keyword: String, page: Int, size: Int) = contentRetrofitService.getSearch(keyword, page, size)

    suspend fun modifyContentCheck(content: Content, userId: Long) = contentRetrofitService.modifyContent(content, userId)

    suspend fun removeContentCheck(contentId: Long) = contentRetrofitService.removeContent(contentId)
}