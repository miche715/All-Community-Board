package com.example.server.domain

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Content(@Id
                   @GeneratedValue(strategy = GenerationType.IDENTITY)
                   @JsonProperty(value = "content_id")
                   @Column(name = "content_id")
                   var contentId: Long? = null,  // 식별 값

                   var writer: String? = null,  // 게시글을 쓴 유저의 username

                   var title: String? = null,  // 게시글의 제목

                   var text: String? = null,  // 게시글의 본문

                   @JsonProperty(value = "created_at")
                   @Column(name = "created_at")
                   var createdAt: LocalDateTime? = null,  // 게시글이 등록된 날짜

                   var good: Int? = null,  // 좋아요

                   var bad: Int? = null,  //싫어요

                   @JoinColumn(name = "user_id")
                   @ManyToOne
                   var userId: User? = null,  // 게시글을 쓴 유저의 식별 값

                   @OneToMany(mappedBy = "content", cascade = [CascadeType.REMOVE])
                   var comment: MutableList<Comment> = mutableListOf()  // 게시글에 달린 댓글 목록
)