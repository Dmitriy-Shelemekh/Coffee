package com.example.coffee

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableTransactionManagement
class CoffeeApplication

fun main(args: Array<String>) {
	runApplication<CoffeeApplication>(*args)
}
