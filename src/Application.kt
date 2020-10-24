package com.gruzini

import com.apurebase.kgraphql.schema.Schema
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.gruzini.authentication.CredentialValidator
import com.gruzini.database.H2DatabaseConfigurer
import com.gruzini.database.PostGresDatabaseConfigurer
import com.gruzini.database.UserGraphSchema
import com.gruzini.models.User
import com.gruzini.repositories.UserRepository
import com.gruzini.requests.GraphQLRequest
import com.gruzini.services.UserService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.util.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

data class JwtConfig(val issuer: String, val realm: String, val secretKey: String, val expirationTime: Int)

fun Application.getJWTConfig(environment: ApplicationEnvironment) = JwtConfig(
    issuer = environment.config.property("jwt.issuer").getString(),
    secretKey = environment.config.property("jwt.secret").getString(),
    realm = environment.config.property("jwt.realm").getString(),
    expirationTime = environment.config.property("jwt.validity_ms").getString().toInt()
)

fun Application.app() {
    //set up beans
    val databaseConfigurer = H2DatabaseConfigurer()
    val db = databaseConfigurer.database()
    val userService = UserService(UserRepository(db))
    val userSchema = UserGraphSchema(userService)
    val credentialValidator = CredentialValidator(userService)

    val jwtConfig = getJWTConfig(environment)
    println("jwtConfig = $jwtConfig")

    //init
    databaseConfigurer.initializeData()

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
        rest(userService)
        graph(userSchema.getSchema())
    }
}


private fun Route.login(jwtConfig: JwtConfig) {
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

private fun Route.rest(userService: UserService) {
    authenticate {
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

private fun Route.graph(schema: Schema) {
    route("/graphql") {
        get("/") {
            val graphRequest = call.receive<GraphQLRequest>()
            call.respond(schema.execute(graphRequest.query))
        }
    }
}

fun generateJWT(user: User, jwtConfig: JwtConfig): String = JWT.create()
    .withSubject("Authentication")
    .withIssuer(jwtConfig.issuer)
    .withClaim("name", user.name)
    .withClaim("password", user.password)
    .withExpiresAt(obtainExpirationDate(jwtConfig.expirationTime))
    .sign(Algorithm.HMAC256(jwtConfig.secretKey))

fun obtainExpirationDate(expirationTime: Int) = Date(System.currentTimeMillis() + expirationTime)


