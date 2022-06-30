package com.example.client.user.domain

data class SignInRequest(var username: String? = null,
                         var password: String? = null
)