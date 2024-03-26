package com.example.coffee.controller

import com.example.coffee.model.dto.CoffeeDto
import com.example.coffee.model.entity.Coffee
import com.example.coffee.service.CoffeeService
import kotlinx.coroutines.runBlocking
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping(path = ["/v1/coffee"], produces = ["application/json; charset=utf-8"])
class CoffeeController(
    private val service: CoffeeService
) {

    @GetMapping("/{coffeeId}")
    fun getCoffee(
        @PathVariable coffeeId: UUID
    ): ResponseEntity<Coffee> = runBlocking {
        service.get(coffeeId)
    }

    @GetMapping("/all")
    fun getAllCoffee(): ResponseEntity<List<Coffee>> = runBlocking {
        service.getAll()
    }

    @PutMapping("/{coffeeId}")
    suspend fun putCoffee(
        @PathVariable coffeeId: UUID,
        @RequestBody dto: CoffeeDto
    ): ResponseEntity<Coffee> = runBlocking {
        service.saveOrUpdate(coffeeId, dto)
    }

    @PostMapping
    fun postCoffee(
        @RequestBody dto: CoffeeDto
    ): ResponseEntity<Coffee> = runBlocking {
        service.save(dto)
    }

    @DeleteMapping("/{coffeeId}")
    suspend fun deleteCoffee(
        @PathVariable coffeeId: UUID
    ): ResponseEntity<Coffee> = runBlocking {
        service.delete(coffeeId)
    }
}