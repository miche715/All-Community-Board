package com.example.client.content.service

import com.example.client.jsonconverter.NullOnEmptyConverterFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ContentRetrofitServiceObject
{
    fun getRetrofitInstance(): ContentRetrofitService
    {
        val gson : Gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.5:8080/contents/")
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit.create(ContentRetrofitService::class.java)
    }
}