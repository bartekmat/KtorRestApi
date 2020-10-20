package com.gruzini

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction


fun Application.module() {
    install(ContentNegotiation) {
        jackson {
            //here i can configure jackson mapper
        }
    }
    val db = Database.connect("jdbc:h2:mem:regular;DB_CLOSE_DELAY=-1", "org.h2.Driver")

    //this will create table in h2 db
    transaction {
        SchemaUtils.create(Users)

        Users.insert {
            it[Users.name] = "Tim Buchalka"
            it[Users.age] = 55
        }
        Users.insert {
            it[Users.name] = "Michal Bojanowski"
            it[Users.age] = 31
        }
        Users.insert {
            it[Users.name] = "Vladimir Putin"
            it[Users.age] = 68
        }
    }

    install(Routing) {
        route("/users") {
            get("/") {
                val users = transaction { Users.selectAll().map { Users.toUser(it) } }
                call.respond(users)
            }
            post("/") {
                val receivedUser = call.receive<User>()
                val savedUser = transaction {
                    Users.insert {
                        it[Users.name] = receivedUser.name
                        it[Users.age] = receivedUser.age
                    }
                }
                call.respond(savedUser)
            }
        }
    }
}

fun main(args: Array<String>): Unit {
    embeddedServer(
        Netty,
        port = 8080,
        module = Application::module,
        watchPaths = listOf("com.gruzini")
    ).start(wait = true)
}



