package com.example.coffee.model.dto

import com.example.coffee.model.entity.Coffee
import java.time.LocalDateTime

data class CoffeeDto(
    var name: String,
    var createDate: LocalDateTime? = LocalDateTime.now(),
    var region: Coffee.Region? = Coffee.Region.BRASILIA
)
