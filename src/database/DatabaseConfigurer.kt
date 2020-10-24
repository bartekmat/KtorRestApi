package com.gruzini.database

import org.jetbrains.exposed.sql.Database

interface DatabaseConfigurer {
    fun initializeDatabase(): Database
}