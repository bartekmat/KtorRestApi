package com.gruzini.database

import com.apurebase.kgraphql.schema.Schema

interface ISchema {
    fun get(): Schema
}