package com.gruzini.routing

import com.gruzini.config.JwtConfig
import com.gruzini.config.generateJWT
import com.gruzini.models.User
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.login(jwtConfig: JwtConfig) {
    route("login") {
        post {
            val loggingUser = call.receive<User>()
            //if userExists in database and password is correct
            val jwt = generateJWT(user = loggingUser, jwtConfig = jwtConfig)
            call.respond(jwt)
        }
        authenticate {
            get { call.respondText { "Authentication valid" } }
        }
    }
}