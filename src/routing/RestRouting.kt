package com.gruzini.routing

import com.gruzini.models.User
import com.gruzini.services.UserService
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Routing.setRESTEndpoints(userService: UserService) {

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