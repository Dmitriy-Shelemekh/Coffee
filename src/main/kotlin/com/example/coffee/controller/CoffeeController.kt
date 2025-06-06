package com.example.coffee.controller

import com.example.coffee.model.dto.CoffeeDto
import com.example.coffee.model.entity.Coffee
import com.example.coffee.service.CoffeeService
import org.springframework.http.HttpStatus
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
    ): ResponseEntity<Coffee> {
        return service.get(coffeeId)
            .map { entity -> ResponseEntity<Coffee>(entity, HttpStatus.OK) }
            .getOrElse { ResponseEntity<Coffee>(HttpStatus.NOT_FOUND) }
    }

    @GetMapping("/all")
    fun getAllCoffee(): ResponseEntity<List<Coffee>> {
        val coffee = service.getAll()
        return ResponseEntity<List<Coffee>>(coffee, HttpStatus.OK)
    }

    @PutMapping("/{coffeeId}")
    fun putCoffee(
        @PathVariable coffeeId: UUID,
        @RequestBody dto: CoffeeDto
    ): ResponseEntity<Coffee>  {
        return service.get(coffeeId)
            .map { target -> ResponseEntity<Coffee>(service.update(target, dto), HttpStatus.OK) }
            .getOrElse { postCoffee(dto) }
    }

    @PostMapping
    fun postCoffee(
        @RequestBody dto: CoffeeDto
    ): ResponseEntity<Coffee> {
        val coffee = service.save(dto)
        return ResponseEntity<Coffee>(coffee, HttpStatus.CREATED)
    }

    @DeleteMapping("/{coffeeId}")
    fun deleteCoffee(
        @PathVariable coffeeId: UUID
    ): ResponseEntity<Coffee> {
        return ResponseEntity<Coffee>(HttpStatus.OK)
    }
}