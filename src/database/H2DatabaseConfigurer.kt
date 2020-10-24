package com.gruzini.database

import com.gruzini.models.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

class H2DatabaseConfigurer : DatabaseConfigurer {
    override fun database(): Database  = Database.connect("jdbc:h2:mem:regular;DB_CLOSE_DELAY=-1", "org.h2.Driver")
    override fun initializeData() {
        transaction { SchemaUtils.drop(Users) }
        transaction {
            SchemaUtils.create(Users)

            Users.insert {
                it[id] = 1
                it[name] = "Tim Buchalka"
                it[password] = "123"
            }
            Users.insert {
                it[id] = 2
                it[name] = "Hadi Hariri"
                it[password] = "123"
            }
            Users.insert {
                it[id] = 3
                it[name] = "Steve Jobs"
                it[password] = "123"
            }
        }
    }
}
