package com.d2d.allygator.shuttle.repository

import com.d2d.allygator.shuttle.model.Vehicle
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

/**
 *  Basic DB operations for Vehicle class (insert, delete, select)
 */
interface VehicleRepository : MongoRepository<Vehicle, String> {
    fun findAllByDeleted(deleted: Boolean): List<Vehicle>
    fun findByIdAndDeleted(id:String, deleted: Boolean): Optional<Vehicle>
}