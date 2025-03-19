package com.example.coffee.model.entity

import java.sql.ResultSet
import java.sql.SQLException
import java.time.LocalDateTime
import java.util.UUID

data class Coffee(
    val id: UUID,
    val name: String,
    val createDate: LocalDateTime,
)

fun ResultSet.toCoffee(): Coffee =
    if (next()) {
        Coffee(
            id = getString("id").let(UUID::fromString),
            name = getString("name"),
            createDate = getTimestamp("create_date").toLocalDateTime()
        )
    } else {
        throw SQLException("Error: Failed Creating Coffee.")
    }
