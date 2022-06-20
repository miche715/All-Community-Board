package com.example.client.user.domain

import com.google.gson.annotations.SerializedName

data class User(@SerializedName("user_id")
                var userId: Long? = null,

                @SerializedName("username")
                var username: String? = null,

                @SerializedName("password")
                var password: String? = null,

                @SerializedName("name")
                var name: String? = null,

                @SerializedName("email")
                var email: String? = null
)