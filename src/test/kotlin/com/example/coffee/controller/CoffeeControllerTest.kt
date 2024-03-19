package com.example.coffee.controller

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.jdbc.Sql
import org.testcontainers.containers.BindMode
import org.testcontainers.containers.PostgreSQLContainer
import java.util.UUID

@SpringBootTest
class CoffeeControllerTest(
    @Autowired val coffeeController: CoffeeController
) {
    companion object {
        private val db = PostgreSQLContainer("postgres:15")
            .withDatabaseName("test")
            .withClasspathResourceMapping("/sql/create_table-coffee.sql", "/docker-entrypoint-initdb.d/", BindMode.READ_ONLY)

        @BeforeAll
        @JvmStatic
        fun startDBContainer() {
            db.start()
        }

        @AfterAll
        @JvmStatic
        fun stopDBContainer() {
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
    @Order(1)
    fun `dbContainer is running`() {
        Assertions.assertTrue(db.isRunning)
    }

    @Test
    @Order(2)
    @Sql("/sql/insert_into-coffee.sql")
    fun `getCoffee is works`() = runBlocking {
        val resp = coffeeController.getCoffee(UUID.fromString("9ba5f1b2-fc3c-42aa-b4dd-d2ba1f1b4da5"))

        Assertions.assertTrue(resp.body?.name == "Ethiopia")
    }
}