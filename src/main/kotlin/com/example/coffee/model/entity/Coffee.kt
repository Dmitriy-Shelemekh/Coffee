package com.example.coffee.model.entity

import com.example.coffee.model.BaseEntity
import jakarta.annotation.Nonnull
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(schema = "coffee", name = "coffee")
class Coffee(
    @Column(name = "name")
    @Nonnull
    var name: String,

    @Column(name = "create_date")
    @Nonnull
    var createDate: LocalDateTime? = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    @Column(name = "region")
    @Nonnull
    var region: Region
) : BaseEntity() {
    enum class Region { COLUMBIA, ETHIOPIA, BRASILIA }
}