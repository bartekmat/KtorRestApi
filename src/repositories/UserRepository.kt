package com.gruzini.repositories

import com.gruzini.models.User
import com.gruzini.models.Users
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository {
    fun fetchAll(): List<User> {
        return transaction { Users.selectAll().map { Users.toUser(it) } }
    }

    fun create(receivedUser: User) {
        transaction {
            Users.insert {
                it[name] = receivedUser.name
                it[age] = receivedUser.age
            }
        }
    }
}
