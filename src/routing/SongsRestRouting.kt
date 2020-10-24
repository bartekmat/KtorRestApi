package com.gruzini.routing

import com.gruzini.models.Song
import com.gruzini.services.SongService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.rest(service: SongService) {
    authenticate {
        route("/songs") {
            get("/") {
                val songs = service.getAll()
                call.respond(songs)
            }
            get("/{id}") {
                val id = call.parameters["id"]!!.toInt()
                val song = service.getById(id)
                call.respond(song)
            }
            post("/") {
                val receivedSong = call.receive<Song>()
                val savedUser = service.save(receivedSong)
                call.respond(savedUser)
            }
        }
    }
}