package com.example.server.service

import com.example.server.domain.User
import com.example.server.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Transactional
class UserService(@Autowired private val userRepository: UserRepository)
{
    fun signUp(user: User): Boolean  // 회원 가입
    {
        return userRepository.findByUsernameOrEmail(user.username!!, user.email!!).run()
        {
            when(this)
            {
                null -> {
                    userRepository.save(user)
                    true
                }
                else -> false
            }
        }
    }

    fun signIn(username: String, password: String): User?  // 로그인
    {
        return userRepository.findByUsernameAndPassword(username, password).run()
        {
            when(this)
            {
                null -> null
                else -> this
            }
        }
    }

    fun findUsername(name: String, email: String): String?  // 아이디 찾기
    {
        return userRepository.findByNameAndEmail(name, email).run()
        {
            when(this)
            {
                null -> null
                else -> this.username
            }
        }
    }

    fun findPassword(name: String, username: String): String?  // 비밀번호 찾기
    {
        return userRepository.findByNameAndUsername(name, username).run()
        {
            when(this)
            {
                null -> null
                else -> this.password
            }
        }
    }
}