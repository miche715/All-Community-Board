package com.example.server.controller

import com.example.server.domain.Good
import com.example.server.domain.GoodResponse
import com.example.server.service.GoodService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/goods")
class GoodController(@Autowired private var goodService: GoodService)
{
    @PostMapping("/create")
    fun createContent(@RequestBody good: Good, @RequestParam(name = "content_id") contentId: Long, @RequestParam(name = "user_id") userId: Long): GoodResponse
    {
        return GoodResponse().apply()
        {
            this.code = HttpStatus.CREATED.value()
            this.body = goodService.addGood(good, contentId, userId)
            if(this.body == null)
            {
                this.code = HttpStatus.OK.value()
            }
        }
    }
}