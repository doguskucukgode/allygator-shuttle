package com.d2d.allygator.shuttle.rest

import com.d2d.allygator.shuttle.model.Vehicle
import com.d2d.allygator.shuttle.service.VehicleService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(controllers = [VehicleResource::class])
class VehicleResourceTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var vehicleService: VehicleService


    private val VEHICLE_NAME : String = "vehicle1"
    private val VEHICLES_ENDPOINT : String = "/vehicles"

    fun getAsJson(vehicle: Vehicle) : String {
        val objectMapper = ObjectMapper()
        return objectMapper.writeValueAsString(vehicle)
    }

    @Test
    fun testShouldSaveVehicle() {
        val vehicle = Vehicle(VEHICLE_NAME)
        Mockito.`when`(vehicleService.saveVehicle(vehicle)).thenReturn(true)
        mockMvc.perform(MockMvcRequestBuilders.post(VEHICLES_ENDPOINT)
                .accept(MediaType.ALL).content(getAsJson(vehicle)).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().`is`(HttpStatus.CREATED.value()))
    }

    @Test
    fun testShouldNotSaveVehicle() {
        val vehicle = Vehicle(VEHICLE_NAME)
        Mockito.`when`(vehicleService.saveVehicle(vehicle)).thenReturn(false)
        mockMvc.perform(MockMvcRequestBuilders.post(VEHICLES_ENDPOINT)
                .accept(MediaType.ALL).content(getAsJson(vehicle)).contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().`is`(HttpStatus.INTERNAL_SERVER_ERROR.value()))
    }

}