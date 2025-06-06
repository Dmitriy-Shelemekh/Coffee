package com.example.coffee

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.BindMode
import org.testcontainers.containers.PostgreSQLContainer

@SpringBootTest
abstract class BaseIntegrationTest {
    companion object {
        val db: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:15")
            .withDatabaseName("test")
            .withUsername("postgres")
            .withPassword("1234")
            .withClasspathResourceMapping("/sql/create_schema-coffee.sql", "/docker-entrypoint-initdb.d/", BindMode.READ_ONLY)

        @BeforeAll
        @JvmStatic
        fun setUp() {
            db.start()
        }

        @AfterAll
        @JvmStatic
        fun tearDown() {
            db.stop()
        }

        @DynamicPropertySource
        @JvmStatic
        fun registerDBContainer(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", db::getJdbcUrl)
            registry.add("spring.datasource.username", db::getUsername)
            registry.add("spring.datasource.password", db::getPassword)
        }
    }

    @Test
    @Disabled("Шаблон для тестов")
    fun `Given When Then`() {
        //given
        //when
        //then
    }
}