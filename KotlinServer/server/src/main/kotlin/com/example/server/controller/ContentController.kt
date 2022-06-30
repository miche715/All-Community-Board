package com.example.server.controller

import com.example.server.domain.Content
import com.example.server.domain.ContentResponse
import com.example.server.service.ContentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/contents")
class ContentController(@Autowired private var contentService: ContentService)
{
    @PostMapping("/create")  // 게시글 작성
    fun createContent(@RequestBody content: Content, @RequestParam(name = "user_id") userId: Long): ContentResponse // 게시글 작성에 성공하면 만들어진 게시글 리턴
    {
        return ContentResponse().apply()
        {
            code = HttpStatus.CREATED.value()
            body = contentService.addContent(content, userId)
        }
    }

    @GetMapping("/all")  // 모든 게시글 읽기
    fun readAll(@RequestParam page: Int, @RequestParam size: Int): ContentResponse
    {
        val pageRequest = PageRequest.of(page, size)

        return ContentResponse().apply()
        {
            code = HttpStatus.OK.value()
            body = contentService.getAll(pageRequest)
        }
    }

    @GetMapping("/search")
    fun readSearch(@RequestParam keyword: String, @RequestParam page: Int, @RequestParam size: Int): ContentResponse
    {
        val pageRequest = PageRequest.of(page, size, Sort.by("contentId").descending())

        return ContentResponse().apply()
        {
            code = HttpStatus.OK.value()
            body = contentService.getSearch(keyword, pageRequest)
        }
    }

    @PutMapping("/update")  // 게시글 수정
    fun updateContent(@RequestBody content: Content, @RequestParam(name = "user_id") userId: Long): ContentResponse
    {
        return ContentResponse().apply()
        {
            code = HttpStatus.OK.value()
            body = contentService.modifyContent(content, userId)
        }
    }

    @DeleteMapping("/delete")  // 게시글 삭제
    fun deleteContent(@RequestParam(name = "content_id") contentId: Long): ContentResponse
    {
        contentService.removeContent(contentId)

        return ContentResponse().apply()
        {
            code = HttpStatus.OK.value()
            body = true
        }
    }
}