package com.example.server.controller

import com.example.server.domain.Comment
import com.example.server.domain.CommentResponse
import com.example.server.service.CommentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comments")
class CommentController(@Autowired private var commentService: CommentService)
{
    @PostMapping("/create")  // 댓글 작성
    fun createContent(@RequestBody comment: Comment, @RequestParam(name = "content_id") contentId: Long, @RequestParam(name = "user_id") userId: Long): CommentResponse
    {
        return CommentResponse().apply()
        {
            this.code = HttpStatus.CREATED.value()
            this.body = commentService.addComment(comment, contentId, userId)
        }
    }

    @GetMapping("/all")  // 게시글에 달린 모든 댓글 읽기
    fun readAll(@RequestParam(name = "content_id") contentId: Long): CommentResponse
    {
        return CommentResponse().apply()
        {
            this.code = HttpStatus.OK.value()
            this.body = commentService.getAll(contentId)
        }
    }

    @DeleteMapping("/delete")  // 댓글 삭제
    fun deleteContent(@RequestParam(name = "comment_id") commentId: Long): CommentResponse
    {
        return CommentResponse().apply()
        {
            this.code = HttpStatus.OK.value()
            this.body = commentService.removeComment(commentId)
        }
    }
}