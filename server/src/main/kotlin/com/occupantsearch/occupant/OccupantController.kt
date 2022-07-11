package com.occupantsearch.occupant

import com.occupantsearch.db.Database
import com.occupantsearch.image.ImageFaceController
import com.occupantsearch.person.PersonTextSearcher
import com.occupantsearch.properties.PropertiesController
import com.occupantsearch.time.measureDuration
import com.occupantsearch.vk.toDto
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
    private val imageFaceController: ImageFaceController,
    private val postFilter: PostFilter,
) : KoinComponent {
    private val logger = LoggerFactory.getLogger(OccupantController::class.java)
    private val postsRepository = database[WallpostFull::class.java]
    private val occupantsReference = AtomicReference<List<Occupant>>(emptyList())
    private val pageSize = props["server"]["page_size"]!!.toInt()
    private val nameToOccupantReference = AtomicReference<Map<String, Occupant>>(emptyMap())

    fun refresh() = measureDuration {
        postsRepository.getAll()
            .values
            .stream()
            .parallel()
            .filter { postFilter.filter(it) }
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
                    faceImageUrls = entry.second.map { imageFaceController[it] }.flatten(),
                    date = entry.second.minBy { it.date }.date
                )
            }
            .sorted { a, b -> b.date - a.date }
            .collect(Collectors.toList())
            .let { list ->
                occupantsReference.set(list)
                nameToOccupantReference.set(list.associateBy { it.person.fullName })
            }
    }.let { duration -> logger.info("Occupants refreshed in $duration") }

    fun findPosts(name: String) = nameToOccupantReference.get()[name]?.let { occupant ->
        occupant.postIds
            .mapNotNull { postsRepository[it] }
            .map { it.toDto() }
    }

    fun findOccupants(query: String, page: Int) = occupantsReference.get()
        .stream()
        .parallel()
        .filter { it.person.matches(query) }
        .collect(Collectors.toList())
        .let { list ->
            OccupantsDto(
                occupants = list.subList(
                    fromIndex = minOf(list.size, page * pageSize),
                    toIndex = minOf(list.size, (page + 1) * pageSize)
                ).map { o -> o.toDto() },
                foundCount = list.size
            )
        }

    fun getAll(): List<Occupant> = occupantsReference.get()
}
