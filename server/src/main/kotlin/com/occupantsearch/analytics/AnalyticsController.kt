package com.occupantsearch.analytics

import com.occupantsearch.db.Database
import com.occupantsearch.time.secondsStartOfDay
import com.vk.api.sdk.objects.wall.WallpostFull
import org.koin.core.component.KoinComponent
import java.util.concurrent.atomic.AtomicReference
import java.util.stream.Collectors

class AnalyticsController(
    database: Database
) : KoinComponent {
    private val postsRepository = database[WallpostFull::class.java]
    private val postsCountByDateReference = AtomicReference<Map<Long, Long>>(emptyMap())

    fun refresh() = postsCountByDateReference.set(
        postsRepository.getAll().values
            .stream()
            .parallel()
            .map { it.date.secondsStartOfDay }
            .collect(Collectors.groupingByConcurrent({ it }, Collectors.counting()))
            .toSortedMap()
    )

    fun getPostsCountByDate(): Map<Long, Long> = postsCountByDateReference.get()
}