package com.example.coffee.controller

import com.example.coffee.model.entity.Coffee
import com.example.coffee.service.CoffeeService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@WebMvcTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CoffeeControllerWebMvcTest(
    @Autowired val mockMvc: MockMvc,
    @Autowired val objectMapper: ObjectMapper
) {
    @MockBean
    private lateinit var coffeeService: CoffeeService

    @Test
    fun `Given twoCoffee When getAll Then successResponse`() {
        //given
        val all = listOf(
            Coffee(name = "name_1", createDate = LocalDateTime.now()),
            Coffee(name = "name_2", createDate = LocalDateTime.now())
        )

        BDDMockito
            .given(coffeeService.getAll())
            .willReturn(all)

        //when
        val result = mockMvc.perform(get("/v1/coffee/all")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(all)))

        //then
        result
            .andExpect(status().isOk)
            .andDo(print())
    }
}