package com.gruzini.services

import com.gruzini.models.Song
import com.gruzini.repositories.ISongRepository
import io.ktor.features.*

class SongService (private val repository: ISongRepository){
    fun getAll(): List<Song> {
        return repository.fetchAll()
    }

    fun getById(id: Int): Song {
        return repository.fetch(id)?: throw NotFoundException("Song with id = $id not found")
    }

    fun save(receivedSong: Song): Boolean {
        return repository.create(receivedSong)
    }

    fun update(song: Song): Boolean {
        TODO("Not yet implemented")
    }
}