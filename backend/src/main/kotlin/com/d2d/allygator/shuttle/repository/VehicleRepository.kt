package com.d2d.allygator.shuttle.repository

import com.d2d.allygator.shuttle.model.Vehicle
import org.springframework.data.mongodb.repository.MongoRepository

interface VehicleRepository : MongoRepository<Vehicle, String> {
}