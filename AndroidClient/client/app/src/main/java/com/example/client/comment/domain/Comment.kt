package com.example.client.comment.domain

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Comment(@SerializedName("comment_id")
                   var commentId: Long? = null,

                   @SerializedName("writer")
                   var writer: String? = null,

                   @SerializedName("text")
                   var text: String? = null,

                   @SerializedName("created_at")
                   var createdAt: String? = null,
) : Serializable