package com.gruzini.routing

import com.apurebase.kgraphql.KGraphQL
import com.gruzini.requests.GraphQLRequest
import com.gruzini.services.UserService
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Routing.setGraphQLEndpoint(userService: UserService) {
    val schema = KGraphQL.schema {
        configure {
            useDefaultPrettyPrinter = true
        }
        query("users") {
            resolver { ->
                userService.getAll()
            }
        }
        query("user") {
            resolver { id: kotlin.Int ->
                userService.getById(id)
            }
        }
    }
    route("/graphql") {
        get("/") {
            val graphRequest = call.receive<GraphQLRequest>()
            call.respond(schema.execute(graphRequest.query))
        }
    }
}