package com.example.coffee.model.entity

import java.time.LocalDateTime
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(schema = "coffee", name = "coffee")
class Coffee(
    @Column
    var name: String,

    @Column
    var createDate: LocalDateTime
) {
    @Id
    var id: UUID = UUID.randomUUID()
}