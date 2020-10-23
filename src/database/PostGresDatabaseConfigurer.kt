package com.gruzini.database

import org.jetbrains.exposed.sql.Database

class PostGresDatabaseConfigurer : DatabaseConfigurer {
    override fun configure(): Database =
        Database.connect("jdbc:postgresql://localhost:5432/ktor", driver = "org.postgresql.Driver",
            user = "postgres", password = "secret")}