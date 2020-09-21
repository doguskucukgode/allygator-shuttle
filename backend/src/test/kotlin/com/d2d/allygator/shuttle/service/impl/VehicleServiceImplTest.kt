package com.d2d.allygator.shuttle.service.impl

import com.d2d.allygator.shuttle.dto.VehicleDto
import com.d2d.allygator.shuttle.model.Location
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertAll


import com.d2d.allygator.shuttle.model.Vehicle
import com.d2d.allygator.shuttle.repository.VehicleRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime
import java.util.*


@ExtendWith(MockitoExtension::class)
class VehicleServiceImplTest {

    private val VEHICLE_NAME1 = "vehicle1"
    private val LOCATION_LAT  = 10.0
    private val LOCATION_LNG  = 20.0
    private val LOCATION_DATE  = LocalDateTime.parse("2017-09-01T12:00:00")

    private lateinit var vehicleServiceImpl: VehicleServiceImpl
    @Mock private lateinit var vehicleRepository: VehicleRepository

    @BeforeEach
    fun setUp() {
        vehicleServiceImpl = VehicleServiceImpl(vehicleRepository)
    }

    @Test
    fun givenDeletedVehicleWhenSaveVehicleThenAssertVehicleInserted() {
        // Arrange
        val vehicle = Vehicle(id = VEHICLE_NAME1)
        vehicle.deleted = true
        Mockito.`when`(vehicleRepository.save(vehicle)).thenReturn(vehicle)
        // Act
        val result = vehicleServiceImpl.saveVehicle(vehicle)
        // Assert
        assertAll(VEHICLE_NAME1,
                { assertEquals(result, true)},
                { assertEquals(vehicle.deleted, false)})
    }

    @Test
    fun givenVehicleWhenSaveVehicleThenThrowsException() {
        // Arrange
        val vehicle = Vehicle(id = VEHICLE_NAME1)
        vehicle.deleted = true
        Mockito.`when`(vehicleRepository.save(vehicle)).thenThrow(RuntimeException::class.java)
        // Act
        val result = vehicleServiceImpl.saveVehicle(vehicle)
        assertAll(VEHICLE_NAME1,
                { assertEquals(result, false)},
                { assertEquals(vehicle.deleted, false)})
    }

    @Test
    fun givenVehicleWhenUpdateVehicleThenAssertVehicleLocation() {
        // Arrange
        val vehicle = Vehicle(id = VEHICLE_NAME1)
        val location = Location(LOCATION_LAT, LOCATION_LNG, LOCATION_DATE)
        vehicle.deleted = true
        Mockito.`when`(vehicleRepository.save(vehicle)).thenReturn(vehicle)
        Mockito.`when`(vehicleRepository.findByIdAndDeleted(VEHICLE_NAME1, false))
                .thenReturn(Optional.of(vehicle))
        // Act
        val result = vehicleServiceImpl.updateVehicle(VEHICLE_NAME1, location)
        // Assert
        assertAll(VEHICLE_NAME1,
                { assertEquals(result, true)},
                { assertEquals(vehicle.lastLocation, location)})
    }

    @Test
    fun givenNonExistVehicleWhenUpdateVehicleThenAssertVehicleLocation() {
        // Arrange
        val location = Location(LOCATION_LAT, LOCATION_LNG, LOCATION_DATE)
        // Act
        val result = vehicleServiceImpl.updateVehicle(VEHICLE_NAME1, location)
        // Assert
        assertEquals(result, false)
    }

    @Test
    fun givenVehicleWhenUpdateVehicleThenThrowsException() {
        // Arrange
        val vehicle = Vehicle(id = VEHICLE_NAME1)
        val location = Location(LOCATION_LAT, LOCATION_LNG, LOCATION_DATE)
        Mockito.`when`(vehicleRepository.findByIdAndDeleted(VEHICLE_NAME1, false))
                .thenReturn(Optional.of(vehicle))
        Mockito.`when`(vehicleRepository.save(vehicle)).thenThrow(RuntimeException::class.java)
        // Act
        val result = vehicleServiceImpl.updateVehicle(VEHICLE_NAME1, location)
        // Assert
        assertEquals(result, false)
    }

    @Test
    fun givenNonExistVehicleWhenDeleteVehicleThenAssertVehicleLocation() {
        // Arrange and Act
        val result = vehicleServiceImpl.deleteVehicle(VEHICLE_NAME1)
        // Assert
        assertEquals(result, false)
    }

    @Test
    fun givenVehicleWhenDeleteVehicleThenAssertVehicleDeletedTrue() {
        // Arrange
        val vehicle = Vehicle(id = VEHICLE_NAME1)
        Mockito.`when`(vehicleRepository.findByIdAndDeleted(VEHICLE_NAME1, false))
                .thenReturn(Optional.of(vehicle))
        // Act
        val result = vehicleServiceImpl.deleteVehicle(VEHICLE_NAME1)
        // Assert
        assertAll(VEHICLE_NAME1,
                { assertEquals(result, true)},
                { assertEquals(vehicle.deleted, true)})
    }

    @Test
    fun givenVehicleWhenDeleteVehicleThenThrowsException() {
        // Arrange
        val vehicle = Vehicle(id = VEHICLE_NAME1)
        Mockito.`when`(vehicleRepository.findByIdAndDeleted(VEHICLE_NAME1, false))
                .thenReturn(Optional.of(vehicle))
        Mockito.`when`(vehicleRepository.save(vehicle)).thenThrow(RuntimeException::class.java)
        // Act
        val result = vehicleServiceImpl.deleteVehicle(VEHICLE_NAME1)
        // Assert
        assertEquals(result, false)
    }

    @Test
    fun givenWhenFindAllVehiclesWithLocationsThenAssertList() {
        // Arrange
        val location = Location(LOCATION_LAT, LOCATION_LNG, LOCATION_DATE)
        val vehicle = Vehicle(id = VEHICLE_NAME1, lastLocation = location)
        Mockito.`when`(vehicleRepository.findAllByDeletedAndLastLocationNotNull(false))
                .thenReturn(listOf(vehicle))
        // Act
        val result = vehicleServiceImpl.findAllVehiclesWithLocations();
        // Assert
        val vehicleDto = VehicleDto(VEHICLE_NAME1, LOCATION_LAT, LOCATION_LNG, LOCATION_DATE)
        assertAll(VEHICLE_NAME1,
                { assertEquals(1, result.size)},
                { assertEquals(vehicleDto, result[0])})
    }

    @Test
    fun givenWhenFindAllVehiclesThenAssertList() {
        // Arrange
        val vehicle = Vehicle(id = VEHICLE_NAME1)
        Mockito.`when`(vehicleRepository.findAllByDeleted(false))
                .thenReturn(listOf(vehicle))
        // Act
        val result = vehicleServiceImpl.findAllVehicles();
        // Assert
        val vehicleDto = VehicleDto(VEHICLE_NAME1, null, null, null)
        assertAll(VEHICLE_NAME1,
                { assertEquals(1, result.size)},
                { assertEquals(vehicleDto, result[0])})
    }
}