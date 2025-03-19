package com.example.coffee

import com.example.coffee.service.CoffeeService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CoffeeServiceTest: BaseIntegrationTest() {
    private lateinit var coffeeService: CoffeeService

    @Test
    fun testInsertAndGetCoffee() {
        coffeeService = CoffeeService(connection)

        coffeeService.createSchema()
        coffeeService.createTable()

        val brasiliaCoffee = coffeeService.insertCoffee("Brasilia")
        val coffee = coffeeService.getCoffee(brasiliaCoffee.id)

        assertEquals("Brasilia", coffee.name)
    }
}