package com.example.server.controller

import com.example.server.service.CommentService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/comments")
class CommentController(private var commentService: CommentService)
{

}