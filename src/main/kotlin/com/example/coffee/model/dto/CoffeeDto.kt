package com.example.coffee.model.dto

import java.time.LocalDateTime

data class CoffeeDto(
    var name: String,
    var createDate: LocalDateTime?
)
