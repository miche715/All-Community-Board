package com.example.server.commentfunction

import com.example.server.domain.Comment
import com.example.server.domain.Content
import com.example.server.domain.User
import com.example.server.service.CommentService
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
class CommentFunctionTest
{
    @Autowired lateinit var userService: UserService
    @Autowired lateinit var contentService: ContentService
    @Autowired lateinit var commentService: CommentService

    @Test
    fun addCommentTest()
    {
        val user1 = User().apply()
        {
            this.username = "testusername1"
            this.password = "testpassword1"
            this.name = "testname1"
            this.email = "test1@test1.com"
        }.run()
        {
            userService.signUp(this)
            userService.signIn(this.username!!, this.password!!)!!
        }

        val content1 = Content().apply()
        {
            this.writer = user1.username
            this.title = "testtitle1"
            this.text = "testtext1"
            this.userId = user1
        }.run()
        {
            contentService.addContent(this)
        }

        val comment1 = Comment().apply()
        {
            this.writer = user1.username
            this.text = "testcommenttext1"
            this.contentId = content1
            this.userId = user1
        }.run()
        {
            commentService.addComment(this)
        }

        val comment2 = Comment().apply()
        {
            this.writer = user1.username
            this.text = "testcommenttext2"
            this.contentId = content1
            this.userId = user1
        }.run()
        {
            commentService.addComment(this)
        }
    }

    @Test
    fun getAllTest()
    {
        val user1 = User().apply()
        {
            this.username = "testusername1"
            this.password = "testpassword1"
            this.name = "testname1"
            this.email = "test1@test1.com"
        }.run()
        {
            userService.signUp(this)
            userService.signIn(this.username!!, this.password!!)!!
        }

        val content1 = Content().apply()
        {
            this.writer = user1.username
            this.title = "testtitle1"
            this.text = "testtext1"
            this.userId = user1
        }.run()
        {
            contentService.addContent(this)
        }

        val comment1 = Comment().apply()
        {
            this.writer = user1.username
            this.text = "testcommenttext1"
            this.contentId = content1
            this.userId = user1
        }.run()
        {
            commentService.addComment(this)
        }

        val comment2 = Comment().apply()
        {
            this.writer = user1.username
            this.text = "testcommenttext2"
            this.contentId = content1
            this.userId = user1
        }.run()
        {
            commentService.addComment(this)
        }

        commentService.getAll(content1)
    }

    @Test
    @Commit
    fun modifyCommentTest()
    {
        val user1 = User().apply()
        {
            this.username = "testusername1"
            this.password = "testpassword1"
            this.name = "testname1"
            this.email = "test1@test1.com"
        }.run()
        {
            userService.signUp(this)
            userService.signIn(this.username!!, this.password!!)!!
        }

        val content1 = Content().apply()
        {
            this.writer = user1.username
            this.title = "testtitle1"
            this.text = "testtext1"
            this.userId = user1
        }.run()
        {
            contentService.addContent(this)
        }

        val comment1 = Comment().apply()
        {
            this.writer = user1.username
            this.text = "testcommenttext1"
            this.contentId = content1
            this.userId = user1
        }.run()
        {
            commentService.addComment(this)
        }

        val comment2 = Comment().apply()
        {
            this.writer = user1.username
            this.text = "testcommenttext2"
            this.contentId = content1
            this.userId = user1
        }.run()
        {
            commentService.addComment(this)
        }

        println(commentService.getAll(content1))

        val comment3 = Comment().apply()
        {
            this.commentId = comment2.commentId
            this.writer = user1.username
            this.text = "testcommenttext33333"
            this.contentId = content1
            this.userId = user1
        }.run()
        {
            commentService.modifyComment(this)
        }
    }
}