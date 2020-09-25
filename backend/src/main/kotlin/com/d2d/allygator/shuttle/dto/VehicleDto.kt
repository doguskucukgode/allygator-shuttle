package com.d2d.allygator.shuttle.dto

import java.time.LocalDateTime

data class VehicleDto(val id: String,
                      val lat: Double? = null,
                      val lng: Double? = null,
                      val at: LocalDateTime? = null,
                      val direction: Double? = null)
