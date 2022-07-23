package com.occupantsearch.geo

import com.occupantsearch.db.Database
import com.occupantsearch.lang.toMap
import com.occupantsearch.natasha.NatashaResponse
import com.occupantsearch.occupant.OccupantController
import com.occupantsearch.time.measureDuration
import org.koin.core.annotation.Single
import org.slf4j.LoggerFactory

@Single
class LocationController(
    database: Database,
    private val occupantController: OccupantController,
) {
    private val logger = LoggerFactory.getLogger(LocationController::class.java)
    private val locationsRepository = database.load(Locations::class.java)
    private val natashaRepository = database.load(NatashaResponse::class.java)

    fun getLocations() = locationsRepository["0"]!!.locations

    fun updateLocations(locations: Map<String, Location>) {
        locationsRepository["0"] = Locations(locations)
    }

    fun get(latitude: Double, longitude: Double, zoom: Int) =
        measureDuration {
            occupantController.getAll()
                .stream()
                .parallel()
                .map { occupant ->
                    occupant.person.fullName to occupant.postIds
                        .mapNotNull { natashaRepository[it]?.locations }
                        .flatten()
                        .distinct()
                        .map {
                            getLocations()[it]!!.let { location ->
                                listOf(location.latitude, location.longitude)
                            }
                        }
                }
                .filter { it.second.isNotEmpty() }
                .toMap()
        }.let {
            logger.info("Map loaded in ${it.duration}")
            it.result
        }
}