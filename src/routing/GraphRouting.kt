package com.gruzini.routing

import com.gruzini.database.ISchema
import com.gruzini.requests.GraphQLRequest
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.graph(schema: ISchema) {
    route("/graphql") {
        get("/") {
            val graphRequest = call.receive<GraphQLRequest>()
            call.respond(schema.get().execute(graphRequest.query))
        }
    }
}