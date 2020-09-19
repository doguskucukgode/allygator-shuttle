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

    /**
     * inserting vehicle
     */
    fun saveVehicle(vehicle: Vehicle): Boolean {
        return try {
            vehicle.deleted = false
            vehicleRepository.save(vehicle);
            true
        } catch (e: RuntimeException) {
            logger.error(e.message)
            false;
        }
    }

    /**
     * Updating vehicle
     */
    fun updateVehicle(id: String, location: Location): Boolean {
        return try {
            val vehicleOptional = vehicleRepository.findByIdAndDeleted(id, false)
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

    /**
     * Deleting vehicle
     */
    fun deleteVehicle(id: String): Boolean {
        return try {
            val vehicleOptional = vehicleRepository.findByIdAndDeleted(id, false)
            if (vehicleOptional.isPresent) {
                val vehicle = vehicleOptional.get()
                vehicle.deleted = true
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

    /**
     * Selecting all vehicles
     */
    fun findAllVehicle(): List<Vehicle> {
        return vehicleRepository.findAllByDeleted(false)
    }
}