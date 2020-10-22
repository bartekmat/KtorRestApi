package com.gruzini.repositories

import com.gruzini.models.User
import com.gruzini.models.Users
import org.jetbrains.exposed.sql.*
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

    override fun update(id: Int, entity: User): Boolean {
        transaction(db) {
            Users.update({Users.id eq id}){
                it[Users.name] = entity.name
                it[Users.age] = entity.age
            }
        }
        return true
    }

    override fun delete(id: Int): Boolean {
        TODO("Not yet implemented")
    }
}
