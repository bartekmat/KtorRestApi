package com.gruzini.services

import com.gruzini.models.User
import com.gruzini.repositories.IUserRepository
import io.ktor.features.*

class UserService(private val repository: IUserRepository) {

    fun getById(id: Int): User {
        return repository.fetch(id)?: throw NotFoundException("User with id = $id not found")
    }

    fun getAll(): List<User> {
        return repository.fetchAll()
    }

    fun save(receivedUser: User): Boolean {
        repository.create(receivedUser)
        return true
    }

    fun update(user: User): Boolean {
        return repository.update(user)
    }

    fun userByLoginExists(username: String): Boolean {
        val user = repository.fetchByUsername(username)
        return user != null
    }

    fun passwordIsCorrect(username: String, password: String): Boolean {
        val user = repository.fetchByUsername(username)!!
        return password == user.password
    }
}