package com.gruzini.database

import com.apurebase.kgraphql.KGraphQL
import com.apurebase.kgraphql.schema.Schema
import com.gruzini.models.Song
import com.gruzini.services.SongService

class SongGraphSchema(private val songService: SongService) {

    fun getSchema(): Schema = KGraphQL.schema {
        configure {
            useDefaultPrettyPrinter = true
        }
        query("songs") {
            resolver { ->
                songService.getAll()
            }
        }
        query("song") {
            resolver { id: Int ->
                songService.getById(id)
            }
        }
        mutation("updateSong") {
            resolver { song: Song ->
                songService.update(song)
            }
        }
    }
}
