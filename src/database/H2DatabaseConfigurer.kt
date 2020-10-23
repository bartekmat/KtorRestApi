package com.gruzini.database

import org.jetbrains.exposed.sql.Database

class H2DatabaseConfigurer : DatabaseConfigurer {
    override fun configure(): Database = Database.connect("jdbc:h2:mem:regular;DB_CLOSE_DELAY=-1", "org.h2.Driver")
}
