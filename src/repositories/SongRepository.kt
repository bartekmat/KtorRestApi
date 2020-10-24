package com.gruzini.repositories

import com.gruzini.models.Song
import com.gruzini.models.Songs
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class SongRepository(private val db: Database) : ISongRepository {
    override fun fetchAll(): List<Song> {
       return transaction(db) {
            Songs.selectAll().map { it.toSong() }
        }
    }

    override fun fetch(id: Int): Song? {
        TODO("Not yet implemented")
    }

    override fun create(entity: Song): Boolean {
        TODO("Not yet implemented")
    }

    override fun update(entity: Song): Boolean {
        TODO("Not yet implemented")
    }

    override fun delete(id: Int): Boolean {
        TODO("Not yet implemented")
    }
}