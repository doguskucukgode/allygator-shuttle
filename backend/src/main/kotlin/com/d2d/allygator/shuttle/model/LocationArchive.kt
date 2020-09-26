package com.d2d.allygator.shuttle.model

import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "locationArchive")
data class LocationArchive (val carId: String, val location: Location)
