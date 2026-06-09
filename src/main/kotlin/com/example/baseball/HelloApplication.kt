package com.example.baseball

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication              // 자동 설정 + 컴포넌트 스캔 + 설정 클래스 표시 = 3-in-1
class HelloApplication

fun main(args: Array<String>) {
    runApplication<HelloApplication>(*args)
}