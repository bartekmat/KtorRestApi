package com.gruzini.services

import com.gruzini.models.Song
import com.gruzini.repositories.ISongRepository
import io.ktor.features.*

class SongService(private val repository: ISongRepository) : Service<Song> {
    override fun getAll(): List<Song> {
        return repository.fetchAll()
    }

    override fun getById(id: Int): Song {
        return repository.fetch(id) ?: throw NotFoundException("Song with id = $id not found")
    }

    override fun save(song: Song): Boolean {
        return repository.create(song)
    }

    override fun update(song: Song): Boolean {
        TODO("Not yet implemented")
    }
}