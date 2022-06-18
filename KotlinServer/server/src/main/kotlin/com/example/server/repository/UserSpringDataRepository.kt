package com.example.server.repository

import com.example.server.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserSpringDataRepository : JpaRepository<User, Long>, UserRepository
{
    override fun findByUsernameOrEmail(username: String, email: String): User?

    override fun findByUsernameAndPassword(username: String, password: String): User?

    override fun findByNameAndEmail(name: String, email: String): User?

    override fun findByNameAndUsername(name: String, username: String): User?
}