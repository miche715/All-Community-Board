package com.example.server.domain

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
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
                   var createdAt: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd HH:mm")),  // 게시글이 등록된 날짜

                   var good: Int? = 0,  // 좋아요

                   var bad: Int? = 0,  //싫어요

                   @ManyToOne
                   @JsonProperty(value = "user_id")
                   @JoinColumn(name = "user_id")
                   var userId: User? = null,  // 게시글을 쓴 유저의 식별 값

                   @OneToMany(mappedBy = "contentId", fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
                   var comments: MutableList<Comment> = mutableListOf()  // 게시글에 달린 댓글 목록
)