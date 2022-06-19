package com.example.server.contentfunction

import com.example.server.domain.Content
import com.example.server.domain.User
import com.example.server.service.ContentService
import com.example.server.service.UserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
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
    fun getContentTest()
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
            contentService.getContent(this.contentId!!)
        }.run()
        {
            Assertions.assertEquals(content.writer, this.writer)
            Assertions.assertEquals(content.title, this.title)
            Assertions.assertEquals(content.text, this.text)
            Assertions.assertEquals(content.userId, this.userId)
        }
    }

    @Test
    fun getAllTest()
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

        val content1 = Content().apply()
        {
            this.writer = user.username
            this.title = "testtitle1"
            this.text = "testtext1"
            this.userId = user
        }
        contentService.addContent(content1)

        val content2 = Content().apply()
        {
            this.writer = user.username
            this.title = "testtitle2"
            this.text = "testtext2"
            this.userId = user
        }
        contentService.addContent(content2)

        val content3 = Content().apply()
        {
            this.writer = user.username
            this.title = "testtitle3"
            this.text = "testtext3"
            this.userId = user
        }
        contentService.addContent(content3)

        with(contentService.getAll())
        {
            Assertions.assertEquals(3, this.size)
        }
    }

    @Test
    fun modifyContentTest()
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

        val content1 = Content().apply()
        {
            this.writer = user.username
            this.title = "testtitle"
            this.text = "testtext"
            this.userId = user
        }.run()
        {
            contentService.addContent(this)
        }

        val content2 = Content().apply()
        {
            this.contentId = content1.contentId
            this.writer = user.username
            this.title = "testtitle1"
            this.text = "testtext1"
            this.userId = user
        }.run()
        {
            contentService.modifyContent(this)
        }

        Assertions.assertEquals(content1.contentId, content2.contentId)
        Assertions.assertEquals(content1.writer, content2.writer)
        Assertions.assertEquals("testtitle1", content2.title)
        Assertions.assertEquals("testtext1", content2.text)
        Assertions.assertEquals(content1.title, content2.title)
        Assertions.assertEquals(content1.text, content2.text)
        Assertions.assertEquals(content1.userId, content2.userId)
    }

    @Test
    fun removeContentTest()
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
        }.run()
        {
            contentService.addContent(this)
        }

        Assertions.assertEquals(contentService.getAll().size, 1)

        contentService.removeContent(content.contentId!!)

        Assertions.assertEquals(contentService.getAll().size, 0)
    }
}