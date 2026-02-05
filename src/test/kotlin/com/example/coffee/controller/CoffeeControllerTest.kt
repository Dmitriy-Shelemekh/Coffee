package com.example.coffee.controller

import com.example.coffee.BaseIntegrationTest
import com.example.coffee.model.dto.CoffeeDto
import com.example.coffee.model.entity.Coffee
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Transactional
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
    @Order(2)
    fun `postCoffee(Save) is works`() {
        Assertions.assertTrue(coffeeController.getAllCoffee().body?.size == 1)
        val coffee = coffeeController.postCoffee(CoffeeDto(name = "Кофе молотый", region = Coffee.Region.COLUMBIA))
        val resp = coffeeController.getCoffee(coffee.body!!.id)
        Assertions.assertEquals("Кофе молотый", resp.body?.name)
        Assertions.assertEquals(2, coffeeController.getAllCoffee().body?.size)
    }

    @Test
    @Order(3)
    fun `putCoffee(Update) is works`() {
        Assertions.assertTrue(coffeeController.getAllCoffee().body?.size == 1)
        val coffee1 = coffeeController.getAllCoffee().body!![0]
        Assertions.assertEquals("Кофе в зернах", coffee1.name)
        coffeeController.putCoffee(coffee1.id, CoffeeDto(name = "Кофе молотый", region = Coffee.Region.BRASILIA)).body!!
        Assertions.assertEquals("Кофе молотый", coffee1.name )
        Assertions.assertEquals(1, coffeeController.getAllCoffee().body?.size)
    }

    @Test
    @Order(4)
    fun `putCoffee(Save) is works`() {
        Assertions.assertTrue(coffeeController.getAllCoffee().body?.size == 1)
        val coffee = coffeeController.putCoffee(UUID.fromString("51dcf966-fbed-424a-840f-498c34f21208"), CoffeeDto(name = "Кофе молотый", region = Coffee.Region.BRASILIA))
        Assertions.assertTrue(coffeeController.getAllCoffee().body?.size == 2)
        Assertions.assertTrue(coffee.statusCode == HttpStatus.CREATED)
        Assertions.assertTrue(coffee.body!!.name == "Кофе молотый")
    }

    @Test
    @Order(5)
    fun `getCoffee is works`() {
        val resp = coffeeController.getCoffee(UUID.fromString("9ba5f1b2-fc3c-42aa-b4dd-d2ba1f1b4da5"))
        Assertions.assertTrue(resp.body?.name == "Кофе в зернах")
        Assertions.assertTrue(coffeeController.getAllCoffee().body?.size == 1)
    }

    @Test
    @Order(6)
    fun `deleteCoffee is works`() {
        val resp = coffeeController.deleteCoffee(UUID.fromString("9ba5f1b2-fc3c-42aa-b4dd-d2ba1f1b4da5"))
        Assertions.assertTrue(resp.statusCode == HttpStatus.OK)
    }

    @Test
    @Order(7)
    fun `getAllCoffee is works`() {
        val resp = coffeeController.getAllCoffee()
        Assertions.assertTrue(resp.body?.isNotEmpty() ?: false)
    }
}