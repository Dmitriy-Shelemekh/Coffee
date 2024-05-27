package com.example.coffee.controller

import com.example.coffee.BaseIntegrationTest
import com.example.coffee.model.dto.CoffeeDto
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql

//@Transactional
@SpringBootTest
@Sql("/sql/insert_into-coffee.sql")
@TestMethodOrder(OrderAnnotation::class)
class CoffeeControllerTest(
    @Autowired val coffeeController: CoffeeController
) : BaseIntegrationTest() {
    @Test
    @Order(1)
    fun `dbContainer is running`() {
        Assertions.assertTrue(db.isRunning)
    }

    @Test
    fun transactionTest() {
        runBlocking {
            repeat(100) {
                launch { coffeeController.testTransaction(CoffeeDto(name = "test transaction")) }
            }
        }
    }
}