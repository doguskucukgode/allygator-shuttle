package com.d2d.allygator.shuttle.service

import com.d2d.allygator.shuttle.dto.VehicleDto
import com.d2d.allygator.shuttle.model.Location
import com.d2d.allygator.shuttle.model.Vehicle

interface VehicleService {

    fun saveVehicle(vehicle: Vehicle): Boolean
    fun updateVehicle(id: String, location: Location): Boolean
    fun deleteVehicle(id: String): Boolean
    fun findAllVehiclesWithLocations(): List<VehicleDto>
    fun findAllVehicles(): List<VehicleDto>
}
