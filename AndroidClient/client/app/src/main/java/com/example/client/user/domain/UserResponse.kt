package com.example.client.user.domain

data class SignUpResponse(var code: Int? = null,
                          var body: Boolean? = null
)

data class SignInResponse(var code: Int? = null,
                          var body: User? = null
)

data class FindUsernameResponse(var code: Int? = null,
                                var body: String? = null
)

data class FindPasswordResponse(var code: Int? = null,
                                var body: String? = null
)