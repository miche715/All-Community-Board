package com.example.server.repository

import com.example.server.domain.User

interface UserRepository
{
    fun save(user: User): User

    fun findById(id: Long): User?

    fun findByUsernameOrEmail(username: String, email: String): User?  // 회원 가입 중복 체크용

    fun findByUsernameAndPassword(username: String, password: String): User?  // 로그인용

    fun findByNameAndEmail(name: String, email: String): User?  // 아이디 찾기용

    fun findByNameAndUsername(name: String, username: String): User?  // 비밀번호 찾기용
}