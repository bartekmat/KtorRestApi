package com.gruzini.services

import com.gruzini.models.Song
import com.gruzini.repositories.ISongRepository

class SongService (private val repository: ISongRepository){
    fun getAll(): List<Song> {
        return repository.fetchAll()
    }

    fun getById(id: Int): Song {
        TODO("Not yet implemented")
    }

    fun save(receivedSong: Song): Song {
        TODO("Not yet implemented")
    }

    fun update(song: Song): Boolean {
        TODO("Not yet implemented")
    }
}