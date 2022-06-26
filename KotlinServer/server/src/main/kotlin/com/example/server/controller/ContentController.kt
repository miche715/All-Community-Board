package com.example.server.controller

import com.example.server.domain.Content
import com.example.server.service.ContentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/contents")
class ContentController(@Autowired private var contentService: ContentService)
{
    @PostMapping("/create")  // 게시글 작성
    fun createContent(@RequestBody content: Content, @RequestParam(name = "user_id") userId: Long): ResponseEntity<Content>  // 게시글 작성에 성공하면 만들어진 게시글 리턴
    {
        return ResponseEntity.status(201).body(contentService.addContent(content, userId))
    }

    @GetMapping("/all")  // 모든 게시글 읽기
    fun readAll(@RequestParam page: Int, @RequestParam size: Int): ResponseEntity<MutableList<Content>>
    {
        val pageRequest = PageRequest.of(page, size)

        return ResponseEntity.status(200).body(contentService.getAll(pageRequest))
    }

    @PutMapping("/update")  // 게시글 수정
    fun updateContent(@RequestBody content: Content, @RequestParam(name = "user_id") userId: Long): ResponseEntity<Content>
    {
        return ResponseEntity.status(200).body(contentService.modifyContent(content, userId))
    }

    @DeleteMapping("/delete")  // 게시글 삭제
    fun deleteContent(@RequestParam(name = "content_id") contentId: Long): ResponseEntity<Boolean>
    {
        contentService.removeContent(contentId)

        return ResponseEntity.status(200).body(true)
    }
}