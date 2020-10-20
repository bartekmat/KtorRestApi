package com.gruzini.bootstrap

import com.gruzini.models.Users
import io.ktor.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction


fun Application.initializeDatabase(): Database {
    val db = Database.connect("jdbc:h2:mem:regular;DB_CLOSE_DELAY=-1", "org.h2.Driver")

    //this will create table in h2 db
    transaction(db) {
        SchemaUtils.create(Users)

        Users.insert {
            it[id] = 1
            it[name] = "Tim Buchalka"
            it[age] = 55
        }
        Users.insert {
            it[id] = 2
            it[name] = "Michal Bojanowski"
            it[age] = 31
        }
        Users.insert {
            it[id] = 3
            it[name] = "Vladimir Putin"
            it[age] = 68
        }
    }
    return db
}