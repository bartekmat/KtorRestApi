package com.gruzini

import com.gruzini.bootstrap.initializeDatabase
import com.gruzini.repositories.UserRepository
import com.gruzini.models.User
import com.gruzini.services.UserService
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*


fun Application.module() {
    val database = initializeDatabase()

    val repository = UserRepository(database)
    val userService = UserService(repository)

    install(ContentNegotiation) {
        jackson {
            //here i can configure jackson mapper
        }
    }

    install(Routing) {
        route("/users") {
            get("/") {
                val users = userService.getAll()
                call.respond(users)
            }
            get("/{id}") {
                val id = call.parameters["id"]!!.toInt()
                println(id)
                val user = userService.getById(id)
                call.respond(user)
            }
            post("/") {
                val receivedUser = call.receive<User>()
                val savedUser = userService.save(receivedUser)
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



