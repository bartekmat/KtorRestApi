package com.gruzini.routing

import com.apurebase.kgraphql.schema.Schema
import com.gruzini.requests.GraphQLRequest
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.graph(schema: Schema) {
    route("/graphql") {
        get("/") {
            val graphRequest = call.receive<GraphQLRequest>()
            call.respond(schema.execute(graphRequest.query))
        }
    }
}