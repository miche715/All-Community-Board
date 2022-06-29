package com.example.client.good.domain

import com.example.client.content.domain.Content

data class AddGoodResponse(var code: Int? = null,
                           var body: Content? = null
)