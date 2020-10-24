package com.gruzini

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.gruzini.authentication.CredentialValidator
import com.gruzini.config.getJWTConfig
import com.gruzini.database.H2DatabaseConfigurer
import com.gruzini.database.SongGraphSchema
import com.gruzini.repositories.SongRepository
import com.gruzini.repositories.UserRepository
import com.gruzini.routing.graph
import com.gruzini.routing.login
import com.gruzini.routing.rest
import com.gruzini.services.SongService
import com.gruzini.services.UserService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.app() {
    //set up beans
    val databaseConfigurer = H2DatabaseConfigurer()
    val db = databaseConfigurer.initializeDatabase()
    val userService = UserService(UserRepository(db))
    val songService = SongService(SongRepository(db))
    val userSchema = SongGraphSchema(songService)
    val credentialValidator = CredentialValidator(userService)

    val jwtConfig = getJWTConfig(environment)
    println("jwtConfig = $jwtConfig")

    install(ContentNegotiation) {
        jackson {
            //here i can configure jackson mapper
        }
    }

    install(Authentication) {
        jwt {
            verifier(JWT.require(Algorithm.HMAC256(jwtConfig.secretKey))
                .withIssuer(jwtConfig.issuer)
                .build())
            validate {
                jwtCredential -> credentialValidator.validate(jwtCredential)
            }
        }
    }

    install(Routing) {
        login(jwtConfig)
        rest(songService)
        graph(userSchema.getSchema())
    }
}