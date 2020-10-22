package com.gruzini

import com.gruzini.modules.routingModule
import com.gruzini.database.UserGraphSchema
import com.gruzini.database.configureDatabase
import com.gruzini.database.initializeData
import com.gruzini.repositories.UserRepository
import com.gruzini.services.UserService
import io.ktor.application.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.app() {
    //set up beans
    val db = configureDatabase()
    val userService = UserService(UserRepository(db))
    val userSchema = UserGraphSchema(userService)

    //set up
    initializeData(db)

    //turn on modules
    routingModule(userService, userSchema)
}


