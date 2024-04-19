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
    fun save(dto: CoffeeDto): Coffee
    fun update(target: Coffee, dto: CoffeeDto): Coffee
    fun saveOrUpdate(coffeeId: UUID, dto: CoffeeDto): Coffee
    fun get(coffeeId: UUID): Optional<Coffee>
    fun getAll(): List<Coffee>
    fun delete(coffeeId: UUID)
}

@Service
class CoffeeServiceImpl(
    private val repository: CoffeeRepository,
    private val mapper: CoffeeMapper
) : CoffeeService {

    @Transactional
    override fun save(
        dto: CoffeeDto
    ): Coffee {
        return repository.save(mapper.toEntity(dto))
    }

    @Transactional
    override fun update(
        target: Coffee, dto: CoffeeDto
    ): Coffee {
        val updated = mapper.update(target, dto)
        return repository.save(updated)
    }

    @Transactional
    override fun saveOrUpdate(
        coffeeId: UUID, dto: CoffeeDto
    ): Coffee {
        val result = repository.findById(coffeeId)
        return if (result.isPresent) update(result.get(), dto) else save(dto)
    }

    @Transactional(readOnly = true)
    override fun get(
        coffeeId: UUID
    ): Optional<Coffee> {
        return repository.findById(coffeeId)
    }

    @Transactional(readOnly = true)
    override fun getAll(): List<Coffee> {
        return repository.findAll()
    }

    @Transactional
    override fun delete(
        coffeeId: UUID
    ) {
        repository.deleteById(coffeeId)
    }
}