package com.gruzini

import com.apurebase.kgraphql.KGraphQL
import com.gruzini.bootstrap.initializeDatabase
import com.gruzini.models.User
import com.gruzini.repositories.IUserRepository
import com.gruzini.repositories.UserRepository
import com.gruzini.requests.GraphQLRequest
import com.gruzini.services.UserService
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.koin.dsl.module.module
import org.koin.ktor.ext.inject
import org.koin.standalone.StandAloneContext

val koinModules = module {
    val database = initializeDatabase()
    single<IUserRepository> { UserRepository(database) }
    single<UserService> { UserService(get()) }
}

fun Application.module() {
    StandAloneContext.startKoin(listOf(koinModules))

    install(ContentNegotiation) {
        jackson {
            //here i can configure jackson mapper
        }
    }

    install(Routing) {
        val userService: UserService by inject()
        setEndpoints(userService)
    }
}

private fun Routing.setEndpoints(userService: UserService) {
    val schema = KGraphQL.schema {
        configure {
            useDefaultPrettyPrinter = true
        }
        query("users") {
            resolver { ->
                userService.getAll()
            }
        }
    }
    route("/graphql") {
        get("/") {
            val graphRequest = call.receive<GraphQLRequest>()
            call.respond(schema.execute(graphRequest.query))
        }
    }
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

fun main(args: Array<String>): Unit {
    embeddedServer(
        Netty,
        port = 8080,
        module = Application::module,
        watchPaths = listOf("com.gruzini")
    ).start(wait = true)
}




