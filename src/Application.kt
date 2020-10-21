package com.gruzini

import com.apurebase.kgraphql.KGraphQL
import com.gruzini.bootstrap.initializeDatabase
import com.gruzini.models.User
import com.gruzini.repositories.IUserRepository
import com.gruzini.repositories.UserRepository
import com.gruzini.requests.GraphQLRequest
import com.gruzini.routing.setGraphQLEndpoint
import com.gruzini.routing.setRESTEndpoints
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
        setRESTEndpoints(userService)
        setGraphQLEndpoint(userService)
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




