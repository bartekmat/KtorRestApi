package com.gruzini.models

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

data class User(val id: Int? = null, val name: String, val password: String) : Entity

object Users : Table() {

    val id: Column<Int> = integer("id").autoIncrement()
    val name: Column<String> = varchar("name", 255)
    val password: Column<String> = varchar("password", 255)

    override val primaryKey = PrimaryKey(id, name = "PK_Users_Id")
}