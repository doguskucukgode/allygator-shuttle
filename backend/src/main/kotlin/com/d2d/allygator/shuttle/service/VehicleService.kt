package com.d2d.allygator.shuttle.service

import com.d2d.allygator.shuttle.dto.VehicleDto
import com.d2d.allygator.shuttle.model.Location

interface VehicleService {

    fun saveVehicle(vehicleDto: VehicleDto): Boolean
    fun updateVehicle(id: String, location: Location): Boolean
    fun deleteVehicle(id: String): Boolean
    fun findAllVehiclesWithLocations(): List<VehicleDto>
    fun findAllVehicles(): List<VehicleDto>
}
