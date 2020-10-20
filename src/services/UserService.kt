package com.gruzini.services

import com.gruzini.models.User
import com.gruzini.repositories.Repository

class UserService(private val repository: Repository<User>) {
    fun getAll(): List<User> {
        return repository.fetchAll()
    }

    fun save(receivedUser: User): Boolean {
        repository.create(receivedUser)
        return true
    }
}