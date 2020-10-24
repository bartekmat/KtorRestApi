package com.gruzini.routing

import com.gruzini.models.Entity
import com.gruzini.services.Service
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

inline fun <reified T : Entity> Route.rest(root: String, service: Service<T>) {
    authenticate {
        route("/$root") {
            get("/") {
                val list = service.getAll()
                call.respond(list)
            }
            get("/{id}") {
                val id = call.parameters["id"]!!.toInt()
                val entity = service.getById(id)
                call.respond(entity)
            }
            post("/") {
                val receivedSong = call.receive<T>()
                val savedEntity = service.save(receivedSong)
                call.respond(savedEntity)
            }
        }
    }
}