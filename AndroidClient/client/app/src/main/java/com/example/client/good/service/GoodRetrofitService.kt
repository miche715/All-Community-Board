package com.example.client.good.service

import com.example.client.good.domain.AddGoodResponse
import com.example.client.good.domain.Good
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface GoodRetrofitService
{
    @POST("create")
    suspend fun addGood(@Body good: Good, @Query(value = "content_id") contentId: Long, @Query(value = "user_id") userId: Long): AddGoodResponse
}