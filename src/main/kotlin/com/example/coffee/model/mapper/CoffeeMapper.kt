package com.example.coffee.model.mapper

import com.example.coffee.model.dto.CoffeeDto
import com.example.coffee.model.entity.Coffee
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.NullValueMappingStrategy
import java.time.LocalDateTime

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
abstract class CoffeeMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "newCoffee.name")
    @Mapping(target = "createDate", expression = "java(updateDate(entity, newCoffee))")
    abstract fun update(@MappingTarget entity: Coffee, newCoffee: CoffeeDto): Coffee

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "newCoffee.name")
    @Mapping(target = "createDate", expression = "java(getDate(newCoffee.getCreateDate()))")
    abstract fun toEntity(newCoffee: CoffeeDto): Coffee

    fun getDate(createDate: LocalDateTime?): LocalDateTime = createDate ?: LocalDateTime.now()

    fun updateDate(target: Coffee, newCoffee: CoffeeDto): LocalDateTime = newCoffee.createDate ?: target.createDate
}