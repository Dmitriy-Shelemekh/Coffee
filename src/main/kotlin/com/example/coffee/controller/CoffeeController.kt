package com.example.coffee.controller

import com.example.coffee.model.dto.CoffeeDto
import com.example.coffee.service.CoffeeService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.slf4j.MDCContext
import kotlinx.coroutines.withContext
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/v1/coffee"], produces = ["application/json; charset=utf-8"])
class CoffeeController(
    private val service: CoffeeService
) {
    @PostMapping("/transaction")
    suspend fun testTransaction(@RequestBody dto: CoffeeDto): ResponseEntity<Any> = withContext(Dispatchers.IO + MDCContext()) {
        service.testTransaction(dto)
        ResponseEntity(HttpStatus.OK)
    }
}