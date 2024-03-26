package com.example.coffee.service

import com.example.coffee.model.dto.CoffeeDto
import com.example.coffee.model.entity.Coffee
import com.example.coffee.model.mapper.CoffeeMapper
import com.example.coffee.repository.CoffeeRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.collections.List
import kotlin.jvm.optionals.getOrElse

interface CoffeeService {
    suspend fun save(dto: CoffeeDto): ResponseEntity<Coffee>
    suspend fun saveOrUpdate(coffeeId: UUID, dto: CoffeeDto): ResponseEntity<Coffee>
    suspend fun get(coffeeId: UUID): ResponseEntity<Coffee>
    suspend fun getAll(): ResponseEntity<List<Coffee>>
    suspend fun delete(coffeeId: UUID): ResponseEntity<Coffee>
}

@Service
class CoffeeServiceImpl(
    private val repository: CoffeeRepository,
    private val mapper: CoffeeMapper
) : CoffeeService {

    @Transactional
    override suspend fun save(
        dto: CoffeeDto
    ): ResponseEntity<Coffee> = coroutineScope {
        val result = async { repository.save(mapper.toEntity(dto)) }
        ResponseEntity<Coffee>(result.await(), HttpStatus.CREATED)
    }

    @Transactional
    override suspend fun saveOrUpdate(
        coffeeId: UUID, dto: CoffeeDto
    ): ResponseEntity<Coffee> = coroutineScope {
        val result = async { repository.findById(coffeeId) }.await()
        if (result.isPresent) update(result.get(), dto)
        else save(dto)
    }

    @Transactional
    suspend fun update(
        coffee: Coffee, dto: CoffeeDto
    ): ResponseEntity<Coffee> = coroutineScope {
        val updated = mapper.update(coffee, dto)
        val result = async { repository.save(updated) }
        ResponseEntity<Coffee>(result.await(), HttpStatus.OK)
    }

    @Transactional(readOnly = true)
    override suspend fun get(
        coffeeId: UUID
    ): ResponseEntity<Coffee> = coroutineScope {
        val result = async { repository.findById(coffeeId) }
        result.await()
            .map { entity -> ResponseEntity<Coffee>(entity, HttpStatus.OK) }
            .getOrElse { ResponseEntity<Coffee>(HttpStatus.NOT_FOUND) }
    }

    @Transactional(readOnly = true)
    override suspend fun getAll(): ResponseEntity<List<Coffee>> = coroutineScope {
        val result = async { repository.findAll() }
        ResponseEntity<List<Coffee>>(result.await(), HttpStatus.OK)
    }

    @Transactional
    override suspend fun delete(
        coffeeId: UUID
    ): ResponseEntity<Coffee> = coroutineScope {
        launch { repository.deleteById(coffeeId) }
        ResponseEntity<Coffee>(HttpStatus.OK)
    }
}