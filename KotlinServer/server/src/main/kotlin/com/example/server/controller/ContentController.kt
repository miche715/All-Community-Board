package com.example.server.controller

import com.example.server.service.ContentService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/contents")
class ContentController(private var contentService: ContentService)
{

}