package com.example.server.service

import com.example.server.domain.Content
import com.example.server.domain.Good
import com.example.server.repository.ContentRepository
import com.example.server.repository.GoodRepository
import com.example.server.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Transactional
class GoodService(@Autowired private val goodRepository: GoodRepository,
                  @Autowired private val contentRepository: ContentRepository,
                  @Autowired private val userRepository: UserRepository
)
{
    fun addGood(good: Good, contentId: Long, userId: Long): Content?
    {
        if(goodRepository.findByContentAndUser(contentRepository.findById(contentId), userRepository.findById(userId)!!) == null)
        {
            goodRepository.save(good.apply()
            {
                this.content = contentRepository.findById(contentId)
                this.user = userRepository.findById(userId)
            }).run()
            {
                return contentRepository.findById(contentId).let()
                {
                    it.goods.add(this)
                    it.goodNum = it.goods.size
                    contentRepository.save(it)
                }
            }
        }
        else
        {
            return null
        }
    }
}