package com.example.server.controller

import com.example.server.domain.Content
import com.example.server.service.ContentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/contents")
class ContentController(@Autowired private var contentService: ContentService)
{
    @PostMapping("/create")  // 게시글 작성
    fun createContent(@RequestBody content: Content): ResponseEntity<Content>  // 게시글 작성에 성공하면 만들어진 게시글 리턴
    {
        return ResponseEntity.status(201).body(contentService.addContent(content))
    }

    @GetMapping("/detail")  // 게시글 1개 세부 사항 읽기
    fun readContent(@RequestParam(name = "content_id") contentId: Long): ResponseEntity<Content> // content_id를 받아 해당하는 게시글의 상세 페이지 리턴
    {
        return ResponseEntity.status(200).body(contentService.showContent(contentId))
    }

    fun readAll()  // 모든 게시글 읽기
    {

    }

    fun updateContent()  // 게시글 수정
    {

    }

    fun deleteContent()  // 게시글 삭제
    {

    }

}