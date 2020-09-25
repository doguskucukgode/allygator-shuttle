package com.d2d.allygator.shuttle.util

import kotlin.math.*

class Util {

    companion object {

        fun distance(lat1: Double, lon1: Double, lat2: Double, lon2: Double, unit: String): Double {
            return if (lat1 == lat2 && lon1 == lon2) {
                0.0
            } else {
                val theta = lon1 - lon2
                var dist = sin(Math.toRadians(lat1)) * sin(Math.toRadians(lat2)) + cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) * cos(Math.toRadians(theta))
                dist = acos(dist)
                dist = Math.toDegrees(dist)
                dist *= 60 * 1.1515
                if (unit == "M") {
                    dist *= (1.609344 * 1000)
                } else if (unit == "N") {
                    dist *= 0.8684
                }
                dist
            }
        }

        fun angleBetweenPoints(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
            val deltaY = lon2 - lon1
            val deltaX = lat2 - lat1
            return Math.toDegrees(atan2(deltaY, deltaX))
        }
    }
}