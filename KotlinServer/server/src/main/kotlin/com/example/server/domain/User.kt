package com.example.server.domain

import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*

@Entity
data class User(@Id
                @GeneratedValue(strategy = GenerationType.IDENTITY)
                @JsonProperty(value = "user_id")
                @Column(name = "user_id")
                var userId: Long? = null,  // 식별 값

                var username: String? = null,  // 로그인을 위한 회원의 아이디, 중복 불가

                var password: String? = null,  // 로그인을 위한 회원의 비밀번호

                var name: String? = null,  // 회원의 이름

                var email: String? = null,  // 회원의 이메일, 중복 불가

//                @OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE])
//                var contents: MutableList<Content> = mutableListOf()  // 유저가 작성한 게시글 목록
)
