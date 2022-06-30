package com.example.client.good.repository

import com.example.client.good.domain.Good
import com.example.client.good.service.GoodRetrofitServiceObject

class GoodRepository
{
    private val goodRetrofitService = GoodRetrofitServiceObject.getRetrofitInstance()

    suspend fun addGoodCheck(good: Good, contentId: Long, userId: Long) = goodRetrofitService.addGood(good, contentId, userId)
}