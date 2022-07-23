package com.occupantsearch.geo

import com.occupantsearch.db.Database
import org.koin.core.annotation.Single

@Single
class LocationController(
    database: Database
) {
    private val locationsRepository = database.load(Locations::class.java)

    fun getLocations() = locationsRepository["0"]!!.locations
}