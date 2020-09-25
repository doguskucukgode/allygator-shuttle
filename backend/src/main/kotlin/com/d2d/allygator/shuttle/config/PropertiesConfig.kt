package com.d2d.allygator.shuttle.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class PropertiesConfig (

        @Value(value = "\${location.insert.interval}")
        val locationInsertInterval: Int,

        @Value(value = "\${location.center.radius}")
        val locationCenterRadius: Double,

        @Value(value = "\${location.center.lat}")
        val locationCenterLat: Double,

        @Value(value = "\${location.center.lng}")
        val locationCenterLng: Double,

        @Value(value = "\${location.center.distance.check}")
        val locationCenterDistanceCheck: Boolean
)
