package com.example.server.service

import com.example.server.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Transactional
class UserService(@Autowired private val userRepository: UserRepository)
{

}