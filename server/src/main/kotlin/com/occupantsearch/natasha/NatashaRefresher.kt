package com.occupantsearch.natasha

import com.occupantsearch.db.Database
import com.occupantsearch.geo.LocationController
import com.vk.api.sdk.objects.wall.WallpostFull
import org.koin.core.annotation.Single

@Single
class NatashaRefresher(
    database: Database,
    private val locationController: LocationController,
    private val natashaClient: NatashaClient,
) {
    private val postsRepository = database.load(WallpostFull::class.java)
    private val responsesRepository = database.load(NatashaResponse::class.java)

    fun refresh() = locationController.getLocations().let { locations ->
        postsRepository.getAll().entries
            .stream()
            .parallel()
            .filter { it.key !in responsesRepository.getAll().keys }
            .map {
                it.key to natashaClient.process(it.value.text)
                    .locations.filter { loc -> loc in locations }
            }
            .forEach {
                responsesRepository[it.first] = NatashaResponse(it.second)
            }
    }
}
