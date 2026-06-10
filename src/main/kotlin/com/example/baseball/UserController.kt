package com.example.baseball

import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.apache.juli.logging.Log
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

data class CreateUserRequest(
    @field:NotBlank(message = "이름은 비어 있을 수 없습니다")
    @field:Size(min = 2, max = 30, message = "이름은 2-30자")
    val name: String,

    @field:NotBlank(message= "이메일 필수")
    @field:Email(message = "이메일 형식이 아닙니다")
    val email: String)

data class UpdateNameRequest(
    @field:NotBlank(message = "이름은 비어 있을 수 없습니다")
    @field:Size(min = 2, max = 30)
    val name: String)

@RestController
@RequestMapping("/users")
class UserController (private val service: UserService) {

    @PostMapping
    fun create(@Valid @RequestBody req: CreateUserRequest): ResponseEntity<User> {
        val saved = service.create(req.name, req.email)

        return ResponseEntity.created(URI.create("/users/${saved.id}")).body(saved)
    }

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Long):User = service.findById(id)

    @GetMapping
    fun getAll():List<User> = service.findAll()

    @PatchMapping("/{id}")  // ⭐ Phase 4-A 배운 PATCH (부분 수정)
    fun updateName(@PathVariable id: Long, @Valid @RequestBody req: UpdateNameRequest): User  = service.updateName(id, req.name)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity.noContent().build()
    }
}