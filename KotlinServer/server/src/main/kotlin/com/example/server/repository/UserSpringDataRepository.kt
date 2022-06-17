package com.example.server.repository

import com.example.server.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserSpringDataRepository : JpaRepository<User, Long>, UserRepository
{
}