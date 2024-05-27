package com.example.coffee.service

import com.example.coffee.model.dto.CoffeeDto
import com.example.coffee.model.entity.Coffee
import com.example.coffee.model.mapper.CoffeeMapper
import com.example.coffee.repository.CoffeeRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface CoffeeService {
    fun testTransaction(dto: CoffeeDto): Coffee
}

@Service
class CoffeeServiceImpl(
    private val repository: CoffeeRepository,
    private val mapper: CoffeeMapper
) : CoffeeService {
    val logger: Logger = LoggerFactory.getLogger(javaClass)

    @Transactional
    override fun testTransaction(dto: CoffeeDto): Coffee {
        var result: Coffee?

        runBlocking {
            result = async { suspendSave(dto) }.await()
        }

        runBlocking { suspendFunWithExc() }

        return result!!
    }

    suspend fun suspendFunWithExc() {
        logger.error("Сейчас бросится RuntimeException")
        delay(5_000)
        throw RuntimeException("Пиу-Дыщ!")
    }

    suspend fun suspendSave(dto: CoffeeDto): Coffee {
        return repository.save(mapper.toEntity(dto))
    }
}