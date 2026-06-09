package com.example.baseball

import org.springframework.stereotype.Service

@Service
class GreetingService {
    fun greet(name: String): String = "Hello, $name!"
    fun defaultGreeting():String = "Hello, Spring"
}