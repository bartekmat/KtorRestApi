package com.gruzini.bootstrap

import com.gruzini.models.Users
import io.ktor.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction


fun Application.initializeDatabase(){
    val db = Database.connect("jdbc:h2:mem:regular;DB_CLOSE_DELAY=-1", "org.h2.Driver")

    //this will create table in h2 db
    transaction {
        SchemaUtils.create(Users)

        Users.insert {
            it[name] = "Tim Buchalka"
            it[age] = 55
        }
        Users.insert {
            it[name] = "Michal Bojanowski"
            it[age] = 31
        }
        Users.insert {
            it[name] = "Vladimir Putin"
            it[age] = 68
        }
    }
}