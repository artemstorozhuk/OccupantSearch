package com.occupantsearch.occupant

import com.occupantsearch.db.Database
import com.occupantsearch.image.ImageFaceController
import com.occupantsearch.person.PersonTextSearcher
import com.occupantsearch.properties.PropertiesController
import com.occupantsearch.time.measureDuration
import com.occupantsearch.vk.uniqueId
import com.vk.api.sdk.objects.wall.WallpostFull
import org.koin.core.component.KoinComponent
import org.slf4j.LoggerFactory
import java.util.concurrent.atomic.AtomicReference
import java.util.stream.Collectors

class OccupantController(
    props: PropertiesController,
    database: Database,
    private val personTextSearcher: PersonTextSearcher,
    private val imageFaceController: ImageFaceController
) : KoinComponent {
    private val logger = LoggerFactory.getLogger(OccupantController::class.java)
    private val postsRepository = database[WallpostFull::class.java]
    private val comparator = compareBy<Occupant> { it.person.firstname }.thenBy { it.person.lastname }
    private val occupantsReference = AtomicReference<List<Occupant>>()
    private val pageSize = props["server"]["page_size"]!!.toInt()

    fun refresh() {
        val duration = measureDuration {
            postsRepository.getAll()
                .values
                .stream()
                .parallel()
                .map { personTextSearcher.search(it.text) to it }
                .filter { it.first.isNotEmpty() }
                .flatMap { pair -> pair.first.map { it to pair.second }.stream() }
                .collect(Collectors.groupingByConcurrent { it.first })
                .entries
                .stream()
                .parallel()
                .map { entry -> entry.key to entry.value.map { it.second } }
                .map { entry ->
                    Occupant(
                        person = entry.first,
                        postIds = entry.second.map { it.uniqueId },
                        faceImageUrls = entry.second.map { imageFaceController[it] }.flatten()
                    )
                }
                .sorted(comparator)
                .collect(Collectors.toList())
                .let { occupantsReference.set(it) }
        }
        logger.info("Occupants refreshed in $duration")
    }

    fun findOccupants(query: String, page: Int) = occupantsReference.get()
        .stream()
        .parallel()
        .filter { it.person.matches(query) }
        .collect(Collectors.toList())
        .let {
            it.subList(
                fromIndex = minOf(it.size, page * pageSize),
                toIndex = minOf(it.size, (page + 1) * pageSize)
            )
        }
}
