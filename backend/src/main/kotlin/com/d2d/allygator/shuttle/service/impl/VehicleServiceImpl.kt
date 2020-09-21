package com.d2d.allygator.shuttle.service.impl

import com.d2d.allygator.shuttle.config.PropertiesConfig
import com.d2d.allygator.shuttle.dto.VehicleDto
import com.d2d.allygator.shuttle.model.Location
import com.d2d.allygator.shuttle.model.Vehicle
import com.d2d.allygator.shuttle.repository.VehicleRepository
import com.d2d.allygator.shuttle.service.VehicleService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.temporal.ChronoUnit
import java.util.stream.Collectors


@Service
class VehicleServiceImpl(
        private val vehicleRepository: VehicleRepository,
        private val propertiesConfig: PropertiesConfig
) : VehicleService {
    private val logger = LoggerFactory.getLogger(VehicleServiceImpl::class.java)

    /**
     * inserting vehicle
     * @param vehicle vehicle to be inserted
     */
    override fun saveVehicle(vehicle: Vehicle): Boolean {
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
     *  @param id Vehicle id to be updated
     *  @param location last location to be set to vehicle
     */
    override fun updateVehicle(id: String, location: Location): Boolean {
        return try {
            val vehicleOptional = vehicleRepository.findByIdAndDeleted(id, false)
            if (vehicleOptional.isPresent) {
                val vehicle = vehicleOptional.get()
                if (checkTime(vehicle.lastLocation, location)) {
                    vehicle.lastLocation = location
                    vehicleRepository.save(vehicle)
                    return true
                }
            }
            return false
        } catch (e: RuntimeException) {
            logger.error(e.message)
            false
        }
    }

    private fun checkTime(oldLocation: Location?, newLocation: Location) = oldLocation == null ||
            ChronoUnit.SECONDS.between(oldLocation.at, newLocation.at) > propertiesConfig.locationInsertInterval

    /**
     * Deleting vehicle
     * @param id Vehicle id to be deleted
     */
    override fun deleteVehicle(id: String): Boolean {
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
     * Selecting all vehicles with locations not null
     */
    override fun findAllVehiclesWithLocations(): List<VehicleDto> {
        return vehicleRepository.findAllByDeletedAndLastLocationNotNull(false)
                .stream()
                .map { v -> VehicleDto(v.id, v.lastLocation?.lat, v.lastLocation?.lng, v.lastLocation?.at) }
                .collect(Collectors.toList())
    }

    /**
     * Selecting all vehicles
     */
    override fun findAllVehicles(): List<VehicleDto> {
        return vehicleRepository.findAllByDeleted(false)
                .stream()
                .map { v -> VehicleDto(v.id, v.lastLocation?.lat, v.lastLocation?.lng, v.lastLocation?.at) }
                .collect(Collectors.toList())
    }
}