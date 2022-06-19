package com.example.server.contentfunction

import com.example.server.domain.Content
import com.example.server.domain.User
import com.example.server.service.ContentService
import com.example.server.service.UserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Commit
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class ContentFunctionTest
{
    @Autowired lateinit var userService: UserService
    @Autowired lateinit var contentService: ContentService

    @Test
    fun addContentTest()
    {
        val user = User().apply()
        {
            this.username = "testusername"
            this.password = "testpassword"
            this.name = "testname"
            this.email = "test@test.com"
        }.run()
        {
            userService.signUp(this)
            userService.signIn(this.username!!, this.password!!)!!
        }

        val content = Content().apply()
        {
            this.writer = user.username
            this.title = "testtitle"
            this.text = "testtext"
            this.userId = user
        }

        with(contentService.addContent(content))
        {
            Assertions.assertNotNull(this)
        }
    }

    @Test
    fun showContentTest()
    {
        val user = User().apply()
        {
            this.username = "testusername"
            this.password = "testpassword"
            this.name = "testname"
            this.email = "test@test.com"
        }.run()
        {
            userService.signUp(this)
            userService.signIn(this.username!!, this.password!!)!!
        }

        val content = Content().apply()
        {
            this.writer = user.username
            this.title = "testtitle"
            this.text = "testtext"
            this.userId = user
        }

        with(contentService.addContent(content))
        {
            contentService.showContent(this)
        }.run()
        {
            Assertions.assertEquals(content.writer, this.writer)
            Assertions.assertEquals(content.title, this.title)
            Assertions.assertEquals(content.text, this.text)
            Assertions.assertEquals(content.userId, this.userId)
        }
    }

}