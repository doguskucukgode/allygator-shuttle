package com.d2d.allygator.shuttle.repository

import com.d2d.allygator.shuttle.model.LocationArchive
import com.d2d.allygator.shuttle.model.Vehicle
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

/**
 *  Basic DB operations for LocationArchive class (insert, delete, select)
 */
interface LocationArchiveRepository : MongoRepository<LocationArchive, String> {
}
