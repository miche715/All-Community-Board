package com.example.server.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class User(@field:Id
                @field:GeneratedValue(strategy = GenerationType.IDENTITY)
                var id: Long? = null,  // 식별 값

                var username: String? = null,  // 로그인을 위한 회원의 아이디, 중복 불가

                var password: String? = null,  // 로그인을 위한 회원의 비밀번호

                var name: String? = null,  // 회원의 이름

                var email: String? = null  // 회원의 이메일, 중복 불가
)
