package com.example.client.comment.domain

import com.example.client.content.domain.Content

data class AddCommentResponse(var code: Int? = null,
                              var body: Content? = null
)

data class GetAllResponse(var code: Int? = null,
                          var body: MutableList<Comment>? = null
)

data class RemoveCommentResponse(var code: Int? = null,
                                 var body: Content? = null
)