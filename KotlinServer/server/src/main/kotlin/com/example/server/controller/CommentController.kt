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
    fun createContent(@RequestBody comment: Comment): ResponseEntity<Comment>  // 게시글 작성에 성공하면 만들어진 게시글 리턴
    {
        return ResponseEntity.status(201).body(commentService.addComment(comment))
    }

    @GetMapping("/all")  // 게시글에 달린 모든 댓글 읽기
    fun readAll(@RequestParam content: Content): ResponseEntity<MutableList<Comment>>
    {
        return ResponseEntity.status(200).body(commentService.getAll(content))
    }

    @PutMapping("/update")  // 댓글 수정
    fun updateContent(@RequestBody comment: Comment): ResponseEntity<Comment>
    {
        return ResponseEntity.status(200).body(commentService.modifyComment(comment))
    }

    @DeleteMapping("/delete")  // 댓글 삭제
    fun deleteContent(@RequestParam(name = "comment_id") commentId: Long): ResponseEntity<Boolean>
    {
        commentService.removeComment(commentId)

        return ResponseEntity.status(200).body(true)
    }
}