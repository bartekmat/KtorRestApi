package com.gruzini.database

import com.apurebase.kgraphql.KGraphQL
import com.apurebase.kgraphql.schema.Schema
import com.gruzini.models.Song
import com.gruzini.services.ISongService

class SongGraphSchema(private val service: ISongService) : ISchema {
    override fun get(): Schema = KGraphQL.schema {
        configure {
            useDefaultPrettyPrinter = true
        }
        query("songs") {
            resolver { ->
                service.getAll()
            }
        }
        query("song") {
            resolver { id: Int ->
                service.getById(id)
            }
        }
        mutation("updateSong") {
            resolver { entity: Song ->
                service.update(entity)
            }
        }
    }
}
