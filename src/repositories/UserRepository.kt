package com.gruzini.repositories

import com.gruzini.models.User
import com.gruzini.models.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository(private val db : Database) : IUserRepository {
    override fun fetchAll(): List<User> {
        return transaction(db) { Users.selectAll().map { it.toUser() } }
    }

    override fun fetch(id: Int): User? {
        return transaction(db) { Users.select { Users.id.eq(id) }.firstOrNull()?.toUser()}
    }

    override fun create(receivedUser: User): Boolean {
        transaction(db) {
            Users.insert {
                it[name] = receivedUser.name
                it[age] = receivedUser.age
            }
        }
        return true
    }

    override fun update(entity: User): Boolean {
        TODO("Not yet implemented")
    }

    override fun delete(id: Int): Boolean {
        TODO("Not yet implemented")
    }
}
