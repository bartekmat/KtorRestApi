package com.gruzini.repositories

import com.gruzini.models.Entity

interface IRepository<T : Entity> {
    fun fetchAll(): List<T>
    fun fetch(id: Int): T?
    fun create(entity: T): Boolean
    fun update(id: Int, entity: T): Boolean
    fun delete(id: Int): Boolean
}