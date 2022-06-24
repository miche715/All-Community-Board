package com.example.server.domain

import com.fasterxml.jackson.annotation.JsonIgnore
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

                   @JsonProperty(value = "good_num")
                   @Column(name = "good_num")
                   var goodNum: Int? = 0,  // 좋아요

                   @ManyToOne
                   @JsonIgnore
                   @JoinColumn(name = "user_id")
                   var user: User? = null,  // 게시글을 쓴 유저의 식별 값

                   @OneToMany(mappedBy = "content", fetch = FetchType.EAGER, cascade = [CascadeType.REMOVE])
                   var comments: MutableList<Comment> = mutableListOf(),  // 게시글에 달린 댓글 목록

                   @JsonProperty("comment_num")
                   var commentNum: Int = comments.size
)