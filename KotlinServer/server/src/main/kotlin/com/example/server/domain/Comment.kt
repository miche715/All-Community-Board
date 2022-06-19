package com.example.server.domain

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Comment(@Id
                   @GeneratedValue(strategy = GenerationType.IDENTITY)
                   @JsonProperty(value = "comment_id")
                   @Column(name = "comment_id")
                   var commentId: Long? = null,  // 식별 값

                   var writer: String? = null,  // 댓글을 쓴 유저의 username

                   var text: String? = null,  // 댓글의 본문

                   @JsonProperty(value = "created_at")
                   @Column(name = "created_at")
                   var createdAt: LocalDateTime? = null,  // 댓글이 등록된 날짜

                   @ManyToOne
                   @JsonProperty(value = "content_id")
                   @JoinColumn(name = "content_id")
                   var contentId: Content? = null,  // 댓글이 달린 게시글

                   @ManyToOne
                   @JsonProperty(value = "user_id")
                   @JoinColumn(name = "user_id")
                   var userId: User? = null,  // 댓글을 쓴 유저
)