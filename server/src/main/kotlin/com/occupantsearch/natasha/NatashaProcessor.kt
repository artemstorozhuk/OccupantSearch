package com.occupantsearch.natasha

import com.occupantsearch.db.Database
import com.occupantsearch.geo.LocationController
import com.vk.api.sdk.objects.wall.WallpostFull
import org.koin.core.annotation.Single
import org.slf4j.LoggerFactory

@Single
class NatashaProcessor(
    database: Database,
    private val locationController: LocationController,
    private val natashaClient: NatashaClient,
) {
    private val logger = LoggerFactory.getLogger(NatashaProcessor::class.java)
    private val postsRepository = database.load(WallpostFull::class.java)
    private val responsesRepository = database.load(NatashaResponse::class.java)

    fun processNewPosts() = locationController.getLocations().let { locations ->
        postsRepository.getAll().entries
            .stream()
            .parallel()
            .filter { it.key !in responsesRepository.getAll().keys }
            .map {
                it.key to natashaClient.query(it.value.text)
                    .locations.filter { loc -> loc in locations }
            }
            .forEach {
                responsesRepository[it.first] = NatashaResponse(it.second)
                logger.info("Processed post ${it.first}")
            }
    }
}
