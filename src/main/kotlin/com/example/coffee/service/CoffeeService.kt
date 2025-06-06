package com.example.coffee.service

import com.example.coffee.model.dto.CoffeeDto
import com.example.coffee.model.dto.toEntity
import com.example.coffee.model.dto.update
import com.example.coffee.model.entity.Coffee
import com.example.coffee.repository.CoffeeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.collections.List

interface CoffeeService {
    fun save(dto: CoffeeDto): Coffee
    fun update(target: Coffee, dto: CoffeeDto): Coffee
    fun saveOrUpdate(coffeeId: UUID, dto: CoffeeDto): Coffee
    fun get(coffeeId: UUID): Optional<Coffee>
    fun getAll(): List<Coffee>
    fun delete(coffeeId: UUID)
}

@Service
class CoffeeServiceImpl(
    private val repository: CoffeeRepository
) : CoffeeService {

    @Transactional
    override fun save(
        dto: CoffeeDto
    ): Coffee = dto.toEntity().run(repository::save)

    @Transactional
    override fun update(
        target: Coffee, dto: CoffeeDto
    ): Coffee = target.update(dto).run(repository::save)

    @Transactional
    override fun saveOrUpdate(
        coffeeId: UUID, dto: CoffeeDto
    ): Coffee = repository
        .findById(coffeeId)
        .map { target -> update(target, dto) }
        .orElseGet { save(dto) }

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