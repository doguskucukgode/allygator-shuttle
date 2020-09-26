package com.d2d.allygator.shuttle.service.impl

import com.d2d.allygator.shuttle.util.Util
import com.d2d.allygator.shuttle.config.PropertiesConfig
import com.d2d.allygator.shuttle.dto.VehicleDto
import com.d2d.allygator.shuttle.model.Location
import com.d2d.allygator.shuttle.model.LocationArchive
import com.d2d.allygator.shuttle.model.Vehicle
import com.d2d.allygator.shuttle.repository.LocationArchiveRepository
import com.d2d.allygator.shuttle.repository.VehicleRepository
import com.d2d.allygator.shuttle.service.VehicleService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.temporal.ChronoUnit
import java.util.stream.Collectors

@Service
class VehicleServiceImpl(
        private val vehicleRepository: VehicleRepository,
        private val locationArchiveRepository: LocationArchiveRepository,
        private val propertiesConfig: PropertiesConfig
) : VehicleService {
    private val logger = LoggerFactory.getLogger(VehicleServiceImpl::class.java)

    /**
     * inserting vehicle
     * @param vehicle vehicle to be inserted
     */
    override fun saveVehicle(vehicleDto: VehicleDto): Boolean {
        return try {
            val vehicle = Vehicle(id = vehicleDto.id, deleted = false, visible = false)
            vehicleRepository.save(vehicle);
            logger.debug(String.format("Vehicle %s registered", vehicleDto.id))
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
                    if (!propertiesConfig.locationCenterDistanceCheck
                            || Util.distance(propertiesConfig.locationCenterLat, propertiesConfig.locationCenterLng,
                            location.lat, location.lng, "M") <= propertiesConfig.locationCenterRadius) {
                        vehicle.visible = true;
                        locationArchiveRepository.save(LocationArchive(vehicle.id, location))
                    } else {
                        vehicle.visible = false;
                        logger.warn(String.format("Vehicle %s is out of range", id))
                    }
                    // calculate direction
                    if(vehicle.lastLocation != null) {
                        vehicle.direction = Util.angleBetweenPoints(vehicle.lastLocation!!.lat,
                                vehicle.lastLocation!!.lng, location.lat, location.lng);
                    }
                    vehicle.lastLocation = location
                    vehicleRepository.save(vehicle)
                    logger.debug(String.format("Vehicle %s posted new location %s - %s", id, location.lat, location.lng))
                    return true
                } else {
                    logger.warn(String.format("Vehicle %s tried to post location less than accepted interval", id))
                }
            } else {
                logger.warn(String.format("Vehicle %s is not present, cannot post location", id))
            }
            return false
        } catch (e: RuntimeException) {
            logger.error(e.message)
            false
        }
    }

    /**
     * checking time difference between two location dates
     * @param oldLocation old location instance
     * @param newLocation new location instance
     */
    private fun checkTime(oldLocation: Location?, newLocation: Location) = oldLocation == null ||
            ChronoUnit.SECONDS.between(oldLocation.at, newLocation.at) >= propertiesConfig.locationInsertInterval

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
                logger.debug(String.format("Vehicle %s de-registered", id))
                true
            } else {
                logger.warn(String.format("Vehicle %s is not present, can not delete vehicle", id))
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
        return vehicleRepository.findAllByDeletedAndVisibleAndLastLocationNotNull(false, true)
                .stream()
                .map { v -> VehicleDto(v.id, v.lastLocation?.lat, v.lastLocation?.lng, v.lastLocation?.at, v.direction) }
                .collect(Collectors.toList())
    }

    /**
     * Selecting all vehicles
     */
    override fun findAllVehicles(): List<VehicleDto> {
        return vehicleRepository.findAllByDeleted(false)
                .stream()
                .map { v -> VehicleDto(v.id, v.lastLocation?.lat, v.lastLocation?.lng, v.lastLocation?.at, v.direction) }
                .collect(Collectors.toList())
    }
}
