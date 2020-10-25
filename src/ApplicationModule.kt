package com.gruzini

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.gruzini.config.getJWTConfig
import com.gruzini.routing.graph
import com.gruzini.routing.login
import com.gruzini.routing.rest
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.routing.*

fun Application.app() {
    val jwtConfig = getJWTConfig(environment)
    val context = Context()
    install(ContentNegotiation) {
        jackson {
            //here i can configure jackson mapper
        }
    }
    install(Authentication) {
        jwt {
            verifier(
                JWT.require(Algorithm.HMAC256(jwtConfig.secretKey))
                    .withIssuer(jwtConfig.issuer)
                    .build()
            )
            validate { jwtCredential ->
                context.credentialValidator.validate(jwtCredential)
            }
        }
    }
    install(Routing) {
        login(jwtConfig)
        rest("songs", context.songService)
        graph(context.songSchema)
    }
}