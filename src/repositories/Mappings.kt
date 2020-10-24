package com.gruzini.repositories

import com.gruzini.models.Song
import com.gruzini.models.Songs
import com.gruzini.models.User
import com.gruzini.models.Users
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toUser(): User {
    return User(this[Users.id], this[Users.name], this[Users.password])
}

fun ResultRow.toSong(): Song {
    return Song(this[Songs.id], this[Songs.title], this[Songs.artist], this[Songs.link])
}