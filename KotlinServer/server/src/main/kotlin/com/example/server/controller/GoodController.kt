package com.example.server.controller

import com.example.server.domain.Content
import com.example.server.domain.Good
import com.example.server.service.GoodService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/goods")
class GoodController(@Autowired private var goodService: GoodService)
{
    @PostMapping("/create")
    fun createContent(@RequestBody good: Good, @RequestParam(name = "content_id") contentId: Long, @RequestParam(name = "user_id") userId: Long): ResponseEntity<Content?>
    {
        return ResponseEntity.status(200).body(goodService.addGood(good, contentId, userId))
    }
}