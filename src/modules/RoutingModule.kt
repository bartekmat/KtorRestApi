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


}