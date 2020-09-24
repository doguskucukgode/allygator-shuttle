package com.d2d.allygator.shuttle.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "vehicle")
class Vehicle(@Id var id: String,
              var lastLocation: Location? = null,
              var deleted: Boolean = false,
              var visible: Boolean = false,
              var direction: Double? = null) {

    override fun equals(other: Any?) = (other is Vehicle)
            && id == other.id

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + (lastLocation?.hashCode() ?: 0)
        result = 31 * result + deleted.hashCode()
        return result
    }
}

