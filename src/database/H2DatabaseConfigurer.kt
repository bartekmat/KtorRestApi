package com.gruzini.database

import com.gruzini.models.Songs
import com.gruzini.models.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

class H2DatabaseConfigurer : DatabaseConfigurer {

    override fun initializeDatabase(): Database {
        val database = database()
        transaction { SchemaUtils.drop(Users) }
        transaction {
            SchemaUtils.create(Users)

            Users.insert {
                it[id] = 1
                it[name] = "tim_buchalka"
                it[password] = "123"
            }
            Users.insert {
                it[id] = 2
                it[name] = "hadi_hariri"
                it[password] = "123"
            }
            Users.insert {
                it[id] = 3
                it[name] = "bartekmat"
                it[password] = "123"
            }
        }
        transaction {
            SchemaUtils.create(Songs)

            Songs.insert {
                it[id] = 1
                it[title] = "Sultans of Swing"
                it[artist] = "Dire Straits"
                it[link] = "https://www.youtube.com/watch?v=0fAQhSRLQnM&ab_channel=Diablo4643"
            }
            Songs.insert {
                it[id] = 2
                it[title] = "Money for Nothing"
                it[artist] = "Dire Straits"
                it[link] = "https://www.youtube.com/watch?v=JRDgihVDEko&ab_channel=Diablo4643"
            }
            Songs.insert {
                it[id] = 3
                it[title] = "Walk of Life"
                it[artist] = "Dire Straits"
                it[link] = "https://www.youtube.com/watch?v=kd9TlGDZGkI&ab_channel=DireStraitsVEVO"
            }
        }
        return database
    }

    private fun database() = Database.connect("jdbc:h2:mem:regular;DB_CLOSE_DELAY=-1", "org.h2.Driver")
}
