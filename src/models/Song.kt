package com.gruzini.models

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

data class Song(val id: Int?, val title: String, val artist: String, val link: String) : Entity(id)

object Songs : Table() {

    val id: Column<Int> = integer("id").autoIncrement()
    val title: Column<String> = varchar("title", 255)
    val artist: Column<String> = varchar("artist", 255)
    val link: Column<String> = varchar("link", 255)

    override val primaryKey = PrimaryKey(id, name = "PK_Songs_Id")
}