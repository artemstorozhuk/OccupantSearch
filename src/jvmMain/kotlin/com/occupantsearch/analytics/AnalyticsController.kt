package com.occupantsearch.analytics

import com.occupantsearch.db.Database
import com.occupantsearch.time.secondsToStartOfDay
import com.vk.api.sdk.objects.wall.WallpostFull
import org.koin.core.component.KoinComponent
import java.util.concurrent.atomic.AtomicReference
import java.util.stream.Collectors

class AnalyticsController(
    database: Database
) : KoinComponent {
    private val postRepository = database[WallpostFull::class.java]
    private val postsCountByDateReference = AtomicReference<Map<Long, Long>>()

    fun refresh() = postsCountByDateReference.set(
        postRepository.getAll().values
            .stream()
            .parallel()
            .map { it.date.secondsToStartOfDay() }
            .collect(Collectors.groupingByConcurrent({ it }, Collectors.counting()))
            .toSortedMap()
    )

    fun getPostsCountByDate() = postsCountByDateReference.get()
}