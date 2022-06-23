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
        }

        with(contentService.addContent(content, user.userId!!))
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
        }

        with(contentService.addContent(content, user.userId!!))
        {
            contentService.getContent(this.contentId!!)
        }.run()
        {
            Assertions.assertEquals(content.writer, this.writer)
            Assertions.assertEquals(content.title, this.title)
            Assertions.assertEquals(content.text, this.text)
        }
    }

    @Test
    @Commit
    fun getAllTest()
    {
        val user = User().apply()
        {
            this.username = "testman"
            this.password = "tester1"
            this.name = "테스트"
            this.email = "test@test.com"
        }.run()
        {
            userService.signUp(this)
            userService.signIn(this.username!!, this.password!!)!!
        }

        for(i in 1..20)
        {
            Content().apply()
            {
                this.writer = user.username
                this.title = "제목 테스트${i}"
                this.text = "내용 테스트${i}"
            }.run()
            {
                contentService.addContent(this, user.userId!!)
            }

        }

        with(contentService.getAll())
        {
            Assertions.assertEquals(21, this.size)
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
        }.run()
        {
            contentService.addContent(this, user.userId!!)
        }

        val content2 = Content().apply()
        {
            this.contentId = content1.contentId
            this.writer = user.username
            this.title = "testtitle1"
            this.text = "testtext1"
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
        }.run()
        {
            contentService.addContent(this, user.userId!!)
        }

        Assertions.assertEquals(contentService.getAll().size, 1)

        contentService.removeContent(content.contentId!!)

        Assertions.assertEquals(contentService.getAll().size, 0)
    }
}