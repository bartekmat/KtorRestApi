package com.gruzini.repositories

import com.gruzini.models.Entity

interface Repository<T : Entity> {
    fun fetchAll(): List<T>
    fun fetch(id: Int): List<T>
    fun create(entity: T): Boolean
    fun update(entity: T): Boolean
    fun delete(id: Int): Boolean
}