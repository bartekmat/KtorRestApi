package com.gruzini

import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

data class User(val name: String, val age: Int)

fun Application.module() {
    routing {
        get("/") {
            call.respond(User("Bartek", 31))
        }
    }
    install(ContentNegotiation) {
        jackson {
            //here i can configure jackson mapper
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



