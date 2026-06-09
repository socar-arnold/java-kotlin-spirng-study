package com.example.baseball

import org.apache.juli.logging.Log
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

data class CreateUserRequest(val name: String, val email: String)

@RestController
@RequestMapping("/users")
class UserController (private val repo: UserRepository) {

    @PostMapping
    fun create(@RequestBody req: CreateUserRequest): ResponseEntity<User> {
        val saved = repo.save(User(name=req.name, email = req.email))

        return ResponseEntity.created(URI.create("/users/${saved.id}")).body(saved)
    }

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long): ResponseEntity<User> {
        val user = repo.findById(id).orElse(null)
        return if (user == null) ResponseEntity.notFound().build() // 404
        else ResponseEntity.ok(user) // 200
    }

    @GetMapping
    fun getAll():List<User> = repo.findAll()

    @GetMapping("/by-email")
    fun byEmail(@RequestParam email: String): User? = repo.findByEmail(email)
}