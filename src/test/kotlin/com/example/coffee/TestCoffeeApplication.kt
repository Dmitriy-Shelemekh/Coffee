package com.example.coffee

import org.springframework.boot.fromApplication
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.with

@TestConfiguration(proxyBeanMethods = false)
class TestCoffeeApplication

fun main(args: Array<String>) {
	fromApplication<CoffeeApplication>().with(TestCoffeeApplication::class).run(*args)
}
