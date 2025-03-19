package com.example.coffee.service

import com.example.coffee.model.entity.Coffee
import com.example.coffee.model.entity.toCoffee
import java.sql.Connection
import java.sql.PreparedStatement
import java.time.LocalDateTime
import java.util.UUID

class CoffeeService(private val connection: Connection) {
    fun createSchema() = connection.createStatement().execute("CREATE SCHEMA IF NOT EXISTS coffee")

    fun createTable() = connection.createStatement().execute("""
        CREATE TABLE IF NOT EXISTS coffee.coffee (
            id UUID NOT NULL PRIMARY KEY, 
            name VARCHAR(255) NOT NULL, 
            create_date TIMESTAMP NOT NULL) 
        """.trimIndent()
    )

    fun insertCoffee(name: String): Coffee =
        connection
            .prepareStatement("INSERT INTO coffee.coffee(id, name, create_date) VALUES (?, ?, ?) RETURNING id, name, create_date")
            .apply { setObject(1, UUID.randomUUID()) }
            .apply { setString(2, name) }
            .apply { setObject(3, LocalDateTime.now()) }
            .let(PreparedStatement::executeQuery)
            .toCoffee()

    fun getCoffee(id: UUID): Coffee =
        connection
            .prepareStatement("SELECT * FROM coffee.coffee WHERE coffee.coffee.id = ?")
            .apply { setObject(1, id) }
            .let(PreparedStatement::executeQuery)
            .toCoffee()
}