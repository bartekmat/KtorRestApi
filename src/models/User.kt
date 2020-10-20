package com.gruzini.models

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

data class User(val id: Int? = null, val name: String, val age: Int) : Entity

object Users : Table() {

    val id: Column<Int> = integer("id").autoIncrement()
    val name: Column<String> = varchar("name", 255)
    val age: Column<Int> = integer("age")

    override val primaryKey = PrimaryKey(id, name = "PK_Users_Id")
}