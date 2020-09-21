package com.d2d.allygator.shuttle.rest

import com.d2d.allygator.shuttle.dto.VehicleDto
import com.d2d.allygator.shuttle.model.Location
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
import java.time.LocalDateTime

@WebMvcTest(controllers = [VehicleResource::class])
class VehicleResourceTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var vehicleService: VehicleService

    private val VEHICLE_NAME1 = "vehicle1"
    private val VEHICLE_NAME2 = "vehicle2"
    private val VEHICLES_ENDPOINT = "/vehicles/"
    private val LOCATIONS_ENDPOINT = "/locations/"
    private val VEHICLE_LOCATIONS_ENDPOINT = "/vehicles/locations/"
    private val ALL_VEHICLE_ENDPOINT = "/vehicles/list/"
    private val LOCATION_LAT  = 10.0
    private val LOCATION_LNG  = 20.0
    private val LOCATION_DATE  = LocalDateTime.parse("2017-09-01T12:00:00")
    private val POST_VEHICLE = "{ \"id\": \"vehicle1\" }"
    private val POST_LOCATION = "{ \"lat\": 10.0, \"lng\": 20.0, \"at\": \"2017-09-01T12:00:00Z\" }"
    private val GET_VEHICLE_LOCATION = "[{\"id\":\"vehicle1\",\"lat\":10.0,\"lng\":20.0,\"at\":\"2017-09-01T12:00:00\"}," +
            "{\"id\":\"vehicle2\",\"lat\":10.0,\"lng\":20.0,\"at\":\"2017-09-01T12:00:00\"}]"
    private val APP_JSON = "application/json"

    fun getAsJson(objects: Any) : String {
        val objectMapper = ObjectMapper()
        return objectMapper.writeValueAsString(objects)
    }

    @Test
    fun testShouldSaveVehicle() {
        val vehicle = VehicleDto(id = VEHICLE_NAME1)
        Mockito.`when`(vehicleService.saveVehicle(vehicle)).thenReturn(true)
        mockMvc.perform(MockMvcRequestBuilders.post(VEHICLES_ENDPOINT)
                .accept(MediaType.ALL).content(POST_VEHICLE).contentType(APP_JSON))
                .andExpect(MockMvcResultMatchers.status().`is`(HttpStatus.CREATED.value()))
    }

    @Test
    fun testShouldNotSaveVehicle() {
        val vehicle = VehicleDto(id = VEHICLE_NAME1)
        Mockito.`when`(vehicleService.saveVehicle(vehicle)).thenReturn(false)
        mockMvc.perform(MockMvcRequestBuilders.post(VEHICLES_ENDPOINT)
                .accept(MediaType.ALL).content(getAsJson(vehicle)).contentType(APP_JSON))
                .andExpect(MockMvcResultMatchers.status().`is`(HttpStatus.INTERNAL_SERVER_ERROR.value()))
    }

    @Test
    fun testShouldDeleteVehicle() {
        Mockito.`when`(vehicleService.deleteVehicle(VEHICLE_NAME1)).thenReturn(true)
        mockMvc.perform(MockMvcRequestBuilders.delete(VEHICLES_ENDPOINT + VEHICLE_NAME1)
                .accept(MediaType.ALL).contentType(APP_JSON))
                .andExpect(MockMvcResultMatchers.status().`is`(HttpStatus.NO_CONTENT.value()))
    }

    @Test
    fun testShouldNotDeleteVehicle() {
        Mockito.`when`(vehicleService.deleteVehicle(VEHICLE_NAME1)).thenReturn(false)
        mockMvc.perform(MockMvcRequestBuilders.delete(VEHICLES_ENDPOINT + VEHICLE_NAME1)
                .accept(MediaType.ALL).contentType(APP_JSON))
                .andExpect(MockMvcResultMatchers.status().`is`(HttpStatus.INTERNAL_SERVER_ERROR.value()))
    }

    @Test
    fun testShouldPostLocation() {
        val location = Location(LOCATION_LAT, LOCATION_LNG, LOCATION_DATE)
        Mockito.`when`(vehicleService.updateVehicle(VEHICLE_NAME1, location)).thenReturn(true)
        mockMvc.perform(MockMvcRequestBuilders.post(VEHICLES_ENDPOINT + VEHICLE_NAME1 + LOCATIONS_ENDPOINT)
                .accept(MediaType.ALL).content(POST_LOCATION)
                .contentType(APP_JSON))
                .andExpect(MockMvcResultMatchers.status().`is`(HttpStatus.CREATED.value()))
    }

    @Test
    fun testShouldNotPostLocation() {
        val location = Location(LOCATION_LAT, LOCATION_LNG, LOCATION_DATE)
        Mockito.`when`(vehicleService.updateVehicle(VEHICLE_NAME1, location)).thenReturn(false)
        mockMvc.perform(MockMvcRequestBuilders.post(VEHICLES_ENDPOINT + VEHICLE_NAME1 + LOCATIONS_ENDPOINT)
                .accept(MediaType.ALL).content(POST_LOCATION)
                .contentType(APP_JSON))
                .andExpect(MockMvcResultMatchers.status().`is`(HttpStatus.INTERNAL_SERVER_ERROR.value()))
    }

    @Test
    fun testShouldGetVehiclesWithLocations() {
        Mockito.`when`(vehicleService.findAllVehiclesWithLocations()).thenReturn(sampleVehicleList())
        mockMvc.perform(MockMvcRequestBuilders.get(VEHICLE_LOCATIONS_ENDPOINT)
                .accept(MediaType.ALL).content(POST_LOCATION)
                .contentType(APP_JSON))
                .andExpect(MockMvcResultMatchers.content()
                        .string(GET_VEHICLE_LOCATION))
                .andExpect(MockMvcResultMatchers.status().`is`(HttpStatus.OK.value()))
    }

    @Test
    fun testShouldGetAllVehicles() {
        Mockito.`when`(vehicleService.findAllVehicles()).thenReturn(sampleVehicleList())
        mockMvc.perform(MockMvcRequestBuilders.get(ALL_VEHICLE_ENDPOINT)
                .accept(MediaType.ALL).content(POST_LOCATION)
                .contentType(APP_JSON))
                .andExpect(MockMvcResultMatchers.content()
                        .string(GET_VEHICLE_LOCATION))
                .andExpect(MockMvcResultMatchers.status().`is`(HttpStatus.OK.value()))
    }

    private fun sampleVehicleList() = listOf(
            VehicleDto(VEHICLE_NAME1, LOCATION_LAT, LOCATION_LNG, LOCATION_DATE),
            VehicleDto(VEHICLE_NAME2, LOCATION_LAT, LOCATION_LNG, LOCATION_DATE))
}