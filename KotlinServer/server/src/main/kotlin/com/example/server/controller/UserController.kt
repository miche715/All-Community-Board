package com.example.server.controller

import com.example.server.domain.User
import com.example.server.domain.UserResponse
import com.example.server.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(@Autowired private var userService: UserService)
{
    @PostMapping("/sign-up")  // 회원 가입
    fun createUser(@RequestBody user: User): UserResponse  // 회원 가입 성공 시 true, 실패 시 false 리턴
    {
        return UserResponse().apply()
        {
            this.code = HttpStatus.CREATED.value()
            this.body = userService.signUp(user)
            if(!(this.body as Boolean))
            {
                this.code = HttpStatus.OK.value()
            }
        }
    }

    @GetMapping("/sign-in")  // 로그인
    fun readUser(@RequestParam username: String, @RequestParam password: String): UserResponse // 로그인 성공 시 User, 실패 시 null 리턴
    {
        return UserResponse().apply()
        {
            this.code = HttpStatus.OK.value()
            this.body = userService.signIn(username, password)
        }
    }

    @GetMapping("/find-username")  // 아이디 찾기
    fun readUsername(@RequestParam name: String, @RequestParam email: String): UserResponse  // 아이디 찾기 성공 시 username, 실패 시 null 리턴
    {
        return UserResponse().apply()
        {
            this.code = HttpStatus.OK.value()
            this.body = userService.findUsername(name, email)
        }
    }

    @GetMapping("/find-password")  // 비밀번호 찾기
    fun readPassword(@RequestParam name: String, @RequestParam username: String): UserResponse  // 비밀번호 찾기 성공 시 password, 실패 시 null 리턴
    {
        return UserResponse().apply()
        {
            this.code = HttpStatus.OK.value()
            this.body = userService.findPassword(name, username)
        }
    }
}