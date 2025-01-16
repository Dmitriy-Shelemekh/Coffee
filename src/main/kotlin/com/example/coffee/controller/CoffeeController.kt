package com.example.coffee.controller

import com.example.coffee.model.dto.CoffeeDto
import com.example.coffee.model.entity.Coffee
import com.example.coffee.service.CoffeeService
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.jvm.optionals.getOrElse

@RestController
@RequestMapping(path = ["/v1/coffee"], produces = ["application/json; charset=utf-8"])
class CoffeeController(
    private val service: CoffeeService
) {
    @GetMapping("/{coffeeId}")
    fun getCoffee(
        @PathVariable coffeeId: UUID
    ): ResponseEntity<Coffee> = service
        .get(coffeeId)
        .map { entity -> ResponseEntity<Coffee>(entity, OK) }
        .getOrElse { ResponseEntity.status(NOT_FOUND).build() }

    @GetMapping("/all")
    fun getAllCoffee(): ResponseEntity<List<Coffee>> = service
        .getAll()
        .let { coffee -> ResponseEntity.status(OK).body(coffee) }

    @PutMapping("/{coffeeId}")
    suspend fun putCoffee(
        @PathVariable coffeeId: UUID,
        @RequestBody newCoffee: CoffeeDto
    ): ResponseEntity<Coffee> = service
        .get(coffeeId)
        .map { existedCoffee -> service.update(existedCoffee, newCoffee) }
        .map { updatedCoffee -> ResponseEntity.status(OK).body(updatedCoffee) }
        .getOrElse { postCoffee(newCoffee) }

    @PostMapping
    fun postCoffee(
        @RequestBody newCoffee: CoffeeDto
    ): ResponseEntity<Coffee> = service
        .save(newCoffee)
        .let { coffee -> ResponseEntity.status(CREATED).body(coffee)}

    @DeleteMapping("/{coffeeId}")
    suspend fun deleteCoffee(
        @PathVariable coffeeId: UUID
    ): ResponseEntity<Coffee> = ResponseEntity.status(OK).build()
}