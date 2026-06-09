package com.example.baseball

import jakarta.persistence.*

@Entity                                     // 이 클래스는 테이블 (이름 user)
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   // PK 자동 증가 (DB AUTO_INCREMENT)
    val id: Long = 0,
    var name: String,
    var email: String,
)