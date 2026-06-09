package com.example.baseball

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User? // 메서드 이름만으로 자동 SQL ??
}