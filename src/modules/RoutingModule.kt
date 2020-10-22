package com.gruzini.modules

import com.apurebase.kgraphql.schema.Schema
import com.gruzini.database.UserGraphSchema
import com.gruzini.models.User
import com.gruzini.requests.GraphQLRequest
import com.gruzini.services.UserService
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.routingModule(userService: UserService, userGraphSchema: UserGraphSchema) {

    install(ContentNegotiation) {
        jackson {
            //here i can configure jackson mapper
        }
    }

    install(Routing) {
        rest(userService)
        graph(userGraphSchema.getSchema())
    }
}
private fun Route.rest(userService: UserService){
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
private fun Route.graph(schema: Schema){
    route("/graphql") {
        get("/") {
            val graphRequest = call.receive<GraphQLRequest>()
            call.respond(schema.execute(graphRequest.query))
        }
    }
}