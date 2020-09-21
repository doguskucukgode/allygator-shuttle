package com.d2d.allygator.shuttle.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class PropertiesConfig(
    @Value(value = "\${location.insert.interval}")
    val locationInsertInterval: Int = 3
)