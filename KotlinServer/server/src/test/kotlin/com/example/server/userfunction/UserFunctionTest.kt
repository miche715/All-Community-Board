package com.example.server.userfunction

import com.example.server.domain.User
import com.example.server.service.UserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class UserFunctionTest
{
    @Autowired lateinit var userService: UserService

    @Test
    fun signUpTest()
    {
        with(userService.signUp(User(username = "testusername", password = "testpassword", name = "testname", email = "test@test.com")))  // 회원 가입 테스트
        {
            Assertions.assertEquals(true, this)
        }

        with(userService.signUp(User(username = "testusername", password = "testpassword", name = "testname", email = "test@test.com")))  // username과 email이 존재할 때 테스트
        {
            Assertions.assertEquals(false, this)
        }

        with(userService.signUp(User(username = "testusername", password = "testpassword", name = "testname", email = "test99@test99.com")))  // username이 존재할 때 테스트
        {
            Assertions.assertEquals(false, this)
        }

        with(userService.signUp(User(username = "testusername99", password = "testpassword", name = "testname", email = "test@test.com")))  // email이 존재할 때 테스트
        {
            Assertions.assertEquals(false, this)
        }

        with(userService.signUp(User(username = "testusername99", password = "testpassword", name = "testname", email = "test99@test99.com")))  // password와 name이 존재할 때 테스트
        {
            Assertions.assertEquals(true, this)
        }
    }

    @Test
    fun signInTest()
    {
        val user = User().apply()
        {
            this.username = "testusername"
            this.password = "testpassword"
            this.name = "testname"
            this.email = "test@test.com"
        }
        userService.signUp(user)

        with(userService.signIn(user.username!!, user.password!!))  // username과 password가 모두 일치할 때 테스트
        {
            Assertions.assertEquals(user.username, this?.username)
            Assertions.assertEquals(user.password, this?.password)
            Assertions.assertEquals(user.name, this?.name)
            Assertions.assertEquals(user.email, this?.email)
        }

        with(userService.signIn("wrong username", "wrong password"))  // username과 password가 모두 불일치할 때 테스트
        {
            Assertions.assertEquals(null, this)
        }

        with(userService.signIn("wrong username", user.password!!))  // username이 불일치할 때 테스트
        {
            Assertions.assertEquals(null, this)
        }

        with(userService.signIn(user.username!!, "wrong password"))  // password가 불일치할 때 테스트
        {
            Assertions.assertEquals(null, this)
        }
    }

    @Test
    fun findUsernameTest()
    {
        val user = User().apply()
        {
            this.username = "testusername"
            this.password = "testpassword"
            this.name = "testname"
            this.email = "test@test.com"
        }
        userService.signUp(user)

        with(userService.findUsername(user.name!!, user.email!!))  // name과 email이 모두 일치할 때 테스트
        {
            Assertions.assertEquals(user.username, this)
        }

        with(userService.findUsername("wrong name", "wrong email"))  // name과 email이 모두 불일치할 때 테스트
        {
            Assertions.assertEquals(null, this)
        }

        with(userService.findUsername("wrong name", user.email!!))  // name이 불일치할 때 테스트
        {
            Assertions.assertEquals(null, this)
        }

        with(userService.findUsername(user.name!!, "wrong email"))  // email이 불일치할 때 테스트
        {
            Assertions.assertEquals(null, this)
        }
    }

    @Test
    fun findPasswordTest()
    {
        val user = User().apply()
        {
            this.username = "testusername"
            this.password = "testpassword"
            this.name = "testname"
            this.email = "test@test.com"
        }
        userService.signUp(user)

        with(userService.findPassword(user.name!!, user.username!!))  // name과 username이 모두 일치할 때 테스트
        {
            Assertions.assertEquals(user.password, this)
        }

        with(userService.findPassword("wrong name", "wrong username"))  // name과 username이 모두 불일치할 때 테스트
        {
            Assertions.assertEquals(null, this)
        }

        with(userService.findPassword("wrong name", user.username!!))  // name이 불일치할 때 테스트
        {
            Assertions.assertEquals(null, this)
        }

        with(userService.findPassword(user.name!!, "wrong username"))  // username이 불일치할 때 테스트
        {
            Assertions.assertEquals(null, this)
        }
    }
}