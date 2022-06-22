package com.example.client.content.domain

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Content(@SerializedName("content_id")
                   var contentId: Long? = null,

                   @SerializedName("writer")
                   var writer: String? = null,

                   @SerializedName("title")
                   var title: String? = null,

                   @SerializedName("text")
                   var text: String? = null,

                   @SerializedName("created_at")
                   var createdAt: String? = null,

                   @SerializedName("good")
                   var good: Int? = null,

                   @SerializedName("bad")
                   var bad: Int? = null,

                   @SerializedName("comment_num")
                   var commentNum: Int? = null
) : Serializable
