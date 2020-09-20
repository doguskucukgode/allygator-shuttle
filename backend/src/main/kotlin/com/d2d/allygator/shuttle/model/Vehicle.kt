package com.d2d.allygator.shuttle.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "vehicle")
class Vehicle {

    @Id
    var id: String? = null
    var lastLocation: Location? = null
    var deleted: Boolean = false
}

