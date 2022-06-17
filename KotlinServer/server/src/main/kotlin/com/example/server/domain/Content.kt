package com.example.server.domain

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Content(@Id
                   @GeneratedValue(strategy = GenerationType.IDENTITY)
                   var id: Long? = null,  // 식별 값

                   var username: String? = null,  // 게시글을 쓴 유저의 아이디

                   var title: String? = null,  // 게시글의 제목

                   var text: String? = null,  // 게시글의 본문

                   var createdAt: LocalDateTime? = null  // 게시글이 등록된 날짜
)