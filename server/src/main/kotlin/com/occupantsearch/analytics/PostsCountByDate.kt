package com.occupantsearch.analytics

import com.occupantsearch.db.Database
import com.occupantsearch.time.secondsStartOfDay
import com.vk.api.sdk.objects.wall.WallpostFull
import org.koin.core.annotation.Single
import java.util.concurrent.atomic.AtomicReference
import java.util.stream.Collectors

@Single
class PostsCountByDate(
    database: Database,
) {
    private val postsRepository = database.load(WallpostFull::class.java)
    private val postsCountByDateReference = AtomicReference<Map<Int, Long>>(emptyMap())

    fun update() =
        postsCountByDateReference.set(
            postsRepository.getAll().values
                .stream()
                .parallel()
                .map { it.date.secondsStartOfDay }
                .collect(Collectors.groupingByConcurrent({ it }, Collectors.counting()))
                .toSortedMap()
        )

    fun getPostsCountByDate(): Map<Int, Long> = postsCountByDateReference.get()
}