package com.example.coffee.repository

import org.springframework.data.jpa.repository.JpaRepository
import com.example.coffee.model.entity.Coffee
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CoffeeRepository : JpaRepository<Coffee, UUID>