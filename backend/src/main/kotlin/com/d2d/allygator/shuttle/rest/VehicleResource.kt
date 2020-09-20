package com.d2d.allygator.shuttle.rest

import com.d2d.allygator.shuttle.dto.VehicleDto
import com.d2d.allygator.shuttle.model.Location
import com.d2d.allygator.shuttle.model.Vehicle
import com.d2d.allygator.shuttle.service.VehicleService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/vehicles")
class VehicleResource(
        private val vehicleService: VehicleService
) {

    /**
     * location insert for known vehicle
     */
    @PostMapping("/{id}/locations")
    fun postLocation(@PathVariable("id") id: String, @RequestBody location: Location): ResponseEntity<Void> {
        // TODO: store value in mongo
        if (vehicleService.updateVehicle(id, location)) {
            return ResponseEntity(HttpStatus.CREATED)
        }
        return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
    }

    /**
     * new insert for vehicle
     */
    @PostMapping("")
    fun postVehicle(@RequestBody vehicle: Vehicle): ResponseEntity<Void> {
        if (vehicleService.saveVehicle(vehicle)) {
            return ResponseEntity(HttpStatus.CREATED)
        }
        return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
    }

    /**
     * deleting known vehicle
     */
    @DeleteMapping("/{id}")
    fun deleteVehicle(@PathVariable("id") id: String): ResponseEntity<Void> {
        if (vehicleService.deleteVehicle(id)) {
            return ResponseEntity(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
    }

    /**
     * selecting all vehicles as list
     */
    @GetMapping("/list")
    fun getVehicles(): ResponseEntity<List<VehicleDto>> {
        val allVehicle = vehicleService.findAllVehicle()
        return ResponseEntity(allVehicle, HttpStatus.OK)
    }

}

