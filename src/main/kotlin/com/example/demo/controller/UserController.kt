package com.example.demo.controller

import com.example.demo.model.User
import com.example.demo.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @PostMapping
    fun createUser(@RequestBody user: User): ResponseEntity<User> {
        val savedUser = userService.saveUser(user)
        return ResponseEntity.ok(savedUser)
    }

    @GetMapping
    fun getAllUsers(): ResponseEntity<List<User>> {
        val users = userService.getAllUsers()
        return ResponseEntity.ok(users)
    }
}
