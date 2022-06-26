package com.example.server.controller

import com.example.server.domain.Comment
import com.example.server.domain.Content
import com.example.server.service.CommentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comments")
class CommentController(@Autowired private var commentService: CommentService)
{
    @PostMapping("/create")  // 댓글 작성
    fun createContent(@RequestBody comment: Comment, @RequestParam(name = "content_id") contentId: Long, @RequestParam(name = "user_id") userId: Long): ResponseEntity<Content>
    {
        return ResponseEntity.status(201).body(commentService.addComment(comment, contentId, userId))
    }

    @GetMapping("/all")  // 게시글에 달린 모든 댓글 읽기
    fun readAll(@RequestParam(name = "content_id") contentId: Long): ResponseEntity<MutableList<Comment>>
    {
        return ResponseEntity.status(200).body(commentService.getAll(contentId))
    }

    @PutMapping("/update")  // 댓글 수정
    fun updateContent(@RequestBody comment: Comment): ResponseEntity<Comment>
    {
        return ResponseEntity.status(200).body(commentService.modifyComment(comment))
    }

    @DeleteMapping("/delete")  // 댓글 삭제
    fun deleteContent(@RequestParam(name = "comment_id") commentId: Long): ResponseEntity<Content>
    {
        return ResponseEntity.status(200).body(commentService.removeComment(commentId))
    }
}