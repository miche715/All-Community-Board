package com.example.client.good.domain

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Good(@SerializedName("good_id")
                   var goodId: Long? = null,
) : Serializable