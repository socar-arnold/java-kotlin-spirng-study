package com.example.baseball

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class LoginRequest(val email:String)
data class LoginResponse(val token:String)

@RestController
@RequestMapping("/auth")
class AuthController (
    private val jwtUtil: JwtUtil,
    private val userRepo: UserRepository
){
    @PostMapping("/login")
    fun login(@RequestBody req: LoginRequest): LoginResponse {
        val user = userRepo.findByEmail(req.email) ?: throw NoSuchElementException("user not found: ${req.email}")
        return LoginResponse(token = jwtUtil.generate(user.email))
    }
}