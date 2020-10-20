package com.gruzini.services

import com.gruzini.repositories.UserRepository
import com.gruzini.models.User

class UserService(private val repository: UserRepository) {
    fun getAll(): List<User> {
        return repository.fetchAll()
    }

    fun save(receivedUser: User): Boolean {
        repository.create(receivedUser)
        return true
    }
}