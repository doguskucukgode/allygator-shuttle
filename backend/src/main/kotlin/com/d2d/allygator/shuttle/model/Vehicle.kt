package com.d2d.allygator.shuttle.model

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.ToString
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@ToString
@Document(collection = "vehicle")
class Vehicle {

    @Id
    var id: String? = null
    var lastLocation: Location? = null
    @JsonIgnore
    var deleted: Boolean = false
}

