package com.d2d.allygator.shuttle.service

import com.d2d.allygator.shuttle.model.Location
import com.d2d.allygator.shuttle.model.Vehicle
import com.d2d.allygator.shuttle.repository.VehicleRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class VehicleService(
        private val vehicleRepository: VehicleRepository
) {
    private val logger = LoggerFactory.getLogger(VehicleService::class.java)

    fun saveVehicle(vehicle: Vehicle): Boolean {
        return try {
            vehicleRepository.save(vehicle);
            true
        } catch (e: RuntimeException) {
            logger.error(e.message)
            false;
        }
    }

    fun updateVehicle(id: String, location: Location): Boolean {
        return try {
            val vehicleOptional = vehicleRepository.findById(id)
            if (vehicleOptional.isPresent) {
                val vehicle = vehicleOptional.get()
                vehicle.lastLocation = location
                vehicleRepository.save(vehicle)
                true
            } else {
                false
            }
        } catch (e: RuntimeException) {
            logger.error(e.message)
            false;
        }
    }

    fun deleteVehicle(vehicle: Vehicle): Boolean {
        return try {
            vehicleRepository.delete(vehicle);
            true
        } catch (e: RuntimeException) {
            logger.error(e.message)
            false;
        }
    }

    fun findAllVehicle(): List<Vehicle> {
        return vehicleRepository.findAll()
    }
}