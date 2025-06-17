package com.example.demo.service
import com.example.demo.model.User
import com.example.demo.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val metricsService: MetricsService
) {

    fun saveUser(user: User): User {
        val savedUser = userRepository.save(user)
        metricsService.recordUserCreated(savedUser)
        return savedUser
    }

    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }
}
