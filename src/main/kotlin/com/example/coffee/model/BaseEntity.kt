package com.example.coffee.model

import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import java.util.*

@MappedSuperclass
abstract class BaseEntity {
    @Id
    val id: UUID = UUID.randomUUID()

    override fun hashCode(): Int = id.hashCode()

    override fun toString(): String = "${this.javaClass.simpleName}(id=$id)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return id == (other as BaseEntity).id
    }
}