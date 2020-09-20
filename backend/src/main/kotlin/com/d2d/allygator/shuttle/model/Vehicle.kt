package com.d2d.allygator.shuttle.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "vehicle")
class Vehicle(id: String) {

    @Id
    var id: String? = id
    var lastLocation: Location? = null
    var deleted: Boolean = false

    override fun equals(other: Any?) = (other is Vehicle)
            && id != null
            && other.id != null
            && id == other.id

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (lastLocation?.hashCode() ?: 0)
        result = 31 * result + deleted.hashCode()
        return result
    }
}

