package com.example.coffee

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.testcontainers.containers.PostgreSQLContainer
import java.sql.Connection
import java.sql.DriverManager

abstract class BaseIntegrationTest {
    companion object {
        private val db = PostgreSQLContainer("postgres:15")
            .withDatabaseName("test")

        lateinit var connection: Connection

        @BeforeAll
        @JvmStatic
        fun setUp() {
            db.start()
            connection = DriverManager.getConnection(
                db.jdbcUrl,
                db.username,
                db.password
            )
        }

        @AfterAll
        @JvmStatic
        fun tearDown() = db.stop()
    }

    @Test
    @Disabled("Шаблон для тестов")
    fun `Given When Then`() {
        //given
        //when
        //then
    }
}