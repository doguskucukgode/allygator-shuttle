package com.d2d.allygator.shuttle.model

import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "locationArchive")
class LocationArchive (var carId: String, var location: Location)
