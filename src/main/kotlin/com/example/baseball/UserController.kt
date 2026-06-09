package com.example.baseball

import org.apache.juli.logging.Log
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

data class CreateUserRequest(val name: String, val email: String)
data class UpdateNameRequest(val name: String)
@RestController
@RequestMapping("/users")
class UserController (private val service: UserService) {

    @PostMapping
    fun create(@RequestBody req: CreateUserRequest): ResponseEntity<User> {
        val saved = service.create(req.name, req.email)

        return ResponseEntity.created(URI.create("/users/${saved.id}")).body(saved)
    }

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long):User = service.findById(id)

    @GetMapping
    fun getAll():List<User> = service.findAll()

    @PatchMapping("/{id}")  // ⭐ Phase 4-A 배운 PATCH (부분 수정)
    fun updateName(@PathVariable id: Long, @RequestBody req: UpdateNameRequest): User  = service.updateName(id, req.name)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity.noContent().build()
    }
}