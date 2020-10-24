package com.gruzini.services

import com.gruzini.models.Entity

interface Service<T: Entity> {
    fun getById(id: Int): T
    fun getAll(): List<T>
    fun save(entity: T): Boolean
    fun update(entity: T): Boolean
}