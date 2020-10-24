package com.gruzini.database

import com.gruzini.models.Songs
import com.gruzini.models.Users
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class PostGresDatabaseConfigurer : DatabaseConfigurer {

    override fun initializeDatabase() : Database {
        val database = database()
        transaction {
            //drop tables
            SchemaUtils.drop(Users)
            SchemaUtils.drop(Songs)
            //create tables
            SchemaUtils.create(Users)
            SchemaUtils.create(Songs)
        }
        return database
    }

    private fun database(): Database {
        val config = HikariConfig().apply {
            jdbcUrl = "jdbc:postgresql://localhost:5432/ktor"
            driverClassName = "org.postgresql.Driver"
            username = "postgres"
            password = "secret"
            maximumPoolSize = 10
        }
        val dataSource = HikariDataSource(config)
        return Database.connect(dataSource)
    }
}