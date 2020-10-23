package com.gruzini.database

import com.gruzini.models.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

fun initializeData(db: Database) {
    transaction(db) { SchemaUtils.drop(Users) }
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
}