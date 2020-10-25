package com.gruzini

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.gruzini.authentication.CredentialValidator
import com.gruzini.config.getJWTConfig
import com.gruzini.database.H2Database
import com.gruzini.database.IDatabase
import com.gruzini.database.ISchema
import com.gruzini.database.SongGraphSchema
import com.gruzini.repositories.ISongRepository
import com.gruzini.repositories.IUserRepository
import com.gruzini.repositories.SongRepository
import com.gruzini.repositories.UserRepository
import com.gruzini.routing.graph
import com.gruzini.routing.login
import com.gruzini.routing.rest
import com.gruzini.services.ISongService
import com.gruzini.services.SongService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.routing.*
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.inject
import org.koin.dsl.module


fun main(args: Array<String>): Unit {
    val appModule = module {
        single<IDatabase> { H2Database() }
        single<IUserRepository> { UserRepository(get()) }
        single<ISongRepository> { SongRepository(get()) }
        single<ISongService> { SongService(get()) }
        single<ISchema> { SongGraphSchema(get()) }
        single { CredentialValidator(get()) }
    }

    startKoin {
        modules(appModule)
    }
    return io.ktor.server.netty.EngineMain.main(args)
}

class Context : KoinComponent {
    val credentialValidator by inject<CredentialValidator>()
    val songSchema by inject<ISchema>()
    val songService by inject<ISongService>()
}