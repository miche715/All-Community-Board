package com.example.client.user.service

import com.example.client.jsonconverter.NullOnEmptyConverterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UserRetrofitServiceObject
{
    fun getRetrofitInstance(): UserRetrofitService
    {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.5:8080/users/")
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(UserRetrofitService::class.java)
    }
}