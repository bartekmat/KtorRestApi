package com.gruzini.database

import com.apurebase.kgraphql.KGraphQL
import com.apurebase.kgraphql.schema.Schema
import com.gruzini.services.UserService

class UserGraphSchema(private val userService: UserService) {

    fun getSchema(): Schema = KGraphQL.schema {
        configure {
            useDefaultPrettyPrinter = true
        }
        query("users") {
            resolver { ->
                userService.getAll()
            }
        }
        query("user") {
            resolver { id: kotlin.Int ->
                userService.getById(id)
            }
        }
        mutation("updateUser") {
            resolver { id: Int, name: String, age: Int ->
                userService.update(id, name, age)
            }
        }
    }
}
