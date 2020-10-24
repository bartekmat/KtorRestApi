package com.gruzini.repositories

import com.gruzini.models.User

interface IUserRepository : IRepository<User> {
    fun fetchByUsername(username: String) : User?
}