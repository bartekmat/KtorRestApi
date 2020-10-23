package com.gruzini

import com.gruzini.database.H2DatabaseConfigurer
import com.gruzini.database.PostGresDatabaseConfigurer
import com.gruzini.database.UserGraphSchema
import com.gruzini.database.initializeData
import com.gruzini.modules.routingModule
import com.gruzini.repositories.UserRepository
import com.gruzini.services.UserService
import io.ktor.application.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.app() {
    //set up beans
    val db = PostGresDatabaseConfigurer().configure()
    val userService = UserService(UserRepository(db))
    val userSchema = UserGraphSchema(userService)

    //set up
    initializeData(db)

    //turn on modules
    routingModule(userService, userSchema)
}


