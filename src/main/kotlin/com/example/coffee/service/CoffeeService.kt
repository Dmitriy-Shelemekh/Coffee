package com.example.coffee.service

import com.example.coffee.model.dto.CoffeeDto
import com.example.coffee.model.entity.Coffee
import com.example.coffee.model.mapper.CoffeeMapper
import com.example.coffee.repository.CoffeeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.collections.List

interface CoffeeService {
    fun get(coffeeId: UUID): Optional<Coffee>
    fun getAll(): List<Coffee>
    fun save(newCoffee: CoffeeDto): Coffee
    fun update(target: Coffee, newCoffee: CoffeeDto): Coffee
    fun delete(coffeeId: UUID)
}

@Service
class CoffeeServiceImpl(
    private val repository: CoffeeRepository,
    private val mapper: CoffeeMapper
) : CoffeeService {

    @Transactional
    override fun save(
        newCoffee: CoffeeDto
    ): Coffee = mapper
        .toEntity(newCoffee)
        .let(repository::save)

    @Transactional
    override fun update(
        target: Coffee,
        newCoffee: CoffeeDto
    ): Coffee = mapper
        .update(target, newCoffee)
        .let { updatedCoffee -> repository.save(updatedCoffee) }

    @Transactional(readOnly = true)
    override fun get(
        coffeeId: UUID
    ): Optional<Coffee> = repository.findById(coffeeId)

    @Transactional(readOnly = true)
    override fun getAll(): List<Coffee> = repository.findAll()

    @Transactional
    override fun delete(
        coffeeId: UUID
    ): Unit = repository.deleteById(coffeeId)
}