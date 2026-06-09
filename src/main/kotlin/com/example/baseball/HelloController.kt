package com.example.baseball

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

data class Greeting(val message: String)

@RestController // 자동 JSON직렬화를 해주는 REST API 컨트롤러
class HelloController (private val greetingService: GreetingService){
    @GetMapping("/hello")
    fun hello():Greeting  = Greeting(greetingService.defaultGreeting())

    @GetMapping("/hello/{name}")
    fun helloName(@PathVariable name: String): Greeting = Greeting(greetingService.greet((name)))
}