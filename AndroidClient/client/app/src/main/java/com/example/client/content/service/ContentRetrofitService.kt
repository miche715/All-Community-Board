package com.example.client.content.service

import com.example.client.content.domain.Content
import retrofit2.Call
import retrofit2.http.GET

interface ContentRetrofitService
{
    @GET("all")
    fun getAll(): Call<MutableList<Content>>
}