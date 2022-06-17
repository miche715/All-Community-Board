package com.example.server.domain

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Comment(@Id
                   @GeneratedValue(strategy = GenerationType.IDENTITY)
                   var id: Long? = null,

                   var contentId: Long? = null,  // 이 댓글이 달려있는 게시글의 식별 값

                   var username: String? = null,  // 댓글을 쓴 유저의 아이디

                   var text: String? = null,  // 댓글의 본문

                   var createdAt: LocalDateTime? = null  // 댓글이 등록된 날짜
)