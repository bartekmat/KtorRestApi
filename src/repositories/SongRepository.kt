package com.gruzini.repositories

import com.gruzini.models.Song
import com.gruzini.models.Songs
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class SongRepository(private val db: Database) : ISongRepository {
    override fun fetchAll(): List<Song> {
        return transaction(db) {
            Songs.selectAll().map { it.toSong() }
        }
    }

    override fun fetch(id: Int): Song? {
        return transaction(db) { Songs.select { Songs.id.eq(id) }.firstOrNull()?.toSong() }
    }

    override fun create(entity: Song): Boolean {
        transaction(db) {
            Songs.insert {
                it[title] = entity.title
                it[artist] = entity.artist
                it[link] = entity.link
            }
        }
        return true
    }

    override fun update(entity: Song): Boolean {
        TODO("Not yet implemented")
    }

    override fun delete(id: Int): Boolean {
        TODO("Not yet implemented")
    }
}