package com.example.baseball

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val repo: UserRepository) {

    @Transactional(readOnly = true)
    fun findAll(): List<User> = repo.findAll()

    @Transactional(readOnly = true)
    fun findById(id:Long):User = repo.findById(id).orElseThrow { NoSuchElementException("user $id not found") }

    @Transactional
    fun create(name: String, email: String): User = repo.save(User(name = name, email = email))

    @Transactional
    fun updateName(id: Long, newName: String): User {
        val user = findById(id) // 1. 영속성 컨텍스트에 들어옴
        user.name = newName //2. 필드만 변경하되 save()호출은 없음
        return user //3. 트랙잭션 종료 시 자동 update
    }

    @Transactional
    fun delete(id: Long) {
        val user = findById(id)
        repo.delete(user)
    }
}