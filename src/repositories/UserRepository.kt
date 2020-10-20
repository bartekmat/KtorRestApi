package com.gruzini.repositories

import com.gruzini.models.User
import com.gruzini.models.Users
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository : Repository<User> {
    override fun fetchAll(): List<User> {
        return transaction { Users.selectAll().map { Users.toUser(it) } }
    }

    override fun fetch(id: Int): List<User> {
        TODO("Not yet implemented")
    }

    override fun create(receivedUser: User): Boolean {
        transaction {
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
