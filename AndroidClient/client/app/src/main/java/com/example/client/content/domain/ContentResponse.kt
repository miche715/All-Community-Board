package com.example.client.content.domain

data class AddContentResponse(var code: Int? = null,
                              var body: Boolean? = null
)

data class ModifyContentResponse(var code: Int? = null,
                                 var body: Content? = null
)

data class RemoveContentResponse(var code: Int? = null,
                                 var body: Boolean? = null
)