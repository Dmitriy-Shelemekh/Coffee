package com.example.coffee.model.entity

import com.example.coffee.model.BaseEntity
import jakarta.annotation.Nonnull
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(schema = "coffee", name = "coffee")
class Coffee(
    @Column(name = "name")
    @Nonnull
    var name: String,

    @Column(name = "create_date")
    @Nonnull
    var createDate: LocalDateTime
) : BaseEntity()