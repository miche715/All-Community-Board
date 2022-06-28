package com.example.client.good.service

import com.example.client.jsonconverter.NullOnEmptyConverterFactory
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GoodRetrofitServiceObject
{
    fun getRetrofitInstance(): GoodRetrofitService
    {
        val gson = GsonBuilder().setLenient().create()  // Gson은 RFC 4627에 의해 구체화된 JSON만 허용하는데, setLenient 속성을 설정하여 더 자유롭게 파싱하도록 함.

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.5:8080/goods/")
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit.create(GoodRetrofitService::class.java)
    }
}