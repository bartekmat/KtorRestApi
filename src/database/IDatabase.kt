package com.gruzini.database

import org.jetbrains.exposed.sql.Database

interface IDatabase {
    fun get(): Database
}