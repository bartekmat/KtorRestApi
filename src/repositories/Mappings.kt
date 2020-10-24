package com.gruzini.repositories

import com.gruzini.models.User
import com.gruzini.models.Users
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toUser(): User {
    return User(this[Users.id], this[Users.name], this[Users.password])
}