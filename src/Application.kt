package com.gruzini

import com.gruzini.database.PostGresDatabaseConfigurer
import com.gruzini.database.UserGraphSchema
import com.gruzini.modules.routingModule
import com.gruzini.repositories.UserRepository
import com.gruzini.services.UserService
import io.ktor.application.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.app() {
    //set up beans
    val databaseConfigurer = PostGresDatabaseConfigurer()
    val db = databaseConfigurer.database()
    val userService = UserService(UserRepository(db))
    val userSchema = UserGraphSchema(userService)

    //init
    databaseConfigurer.initializeData()

    //turn on modules
    routingModule(userService, userSchema)
}


