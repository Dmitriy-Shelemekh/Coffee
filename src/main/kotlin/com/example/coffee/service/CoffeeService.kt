package com.example.coffee.service

import com.example.coffee.model.dto.CoffeeDto
import com.example.coffee.model.entity.Coffee
import com.example.coffee.model.mapper.CoffeeMapper
import com.example.coffee.repository.CoffeeRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.collections.List
import kotlin.jvm.optionals.getOrElse

interface CoffeeService {
    suspend fun postCoffee(dto: CoffeeDto): ResponseEntity<Coffee>
    suspend fun putCoffee(coffeeId: UUID, dto: CoffeeDto): ResponseEntity<Coffee>
    suspend fun getCoffee(coffeeId: UUID): ResponseEntity<Coffee>
    suspend fun getAllCoffee(): ResponseEntity<List<Coffee>>
    suspend fun deleteCoffee(coffeeId: UUID): ResponseEntity<Coffee>
}

@Service
class CoffeeServiceImpl(
    private val repository: CoffeeRepository,
    private val mapper: CoffeeMapper
) : CoffeeService {

    @Transactional
    override suspend fun postCoffee(
        dto: CoffeeDto
    ): ResponseEntity<Coffee> = with(repository) {
        ResponseEntity<Coffee>(save(mapper.toEntity(dto)), HttpStatus.CREATED)
    }

    @Transactional
    override suspend fun putCoffee(
        coffeeId: UUID, dto: CoffeeDto
    ): ResponseEntity<Coffee> = with(repository) {
        findById(coffeeId)
            .map { entity -> ResponseEntity<Coffee>(save(mapper.update(entity, dto)), HttpStatus.OK) }
            .getOrElse { postCoffee(dto) }
    }

    @Transactional(readOnly = true)
    override suspend fun getCoffee(
        coffeeId: UUID
    ): ResponseEntity<Coffee> = with(repository) {
        findById(coffeeId)
            .map { entity -> ResponseEntity<Coffee>(entity, HttpStatus.OK) }
            .getOrElse { ResponseEntity<Coffee>(HttpStatus.NOT_FOUND) }
    }

    @Transactional(readOnly = true)
    override suspend fun getAllCoffee(): ResponseEntity<List<Coffee>> = with(repository) {
        ResponseEntity<List<Coffee>>(findAll(), HttpStatus.OK)
    }

    @Transactional
    override suspend fun deleteCoffee(
        coffeeId: UUID
    ): ResponseEntity<Coffee> = with(repository) {
        deleteById(coffeeId)
        ResponseEntity<Coffee>(HttpStatus.OK)
    }
}