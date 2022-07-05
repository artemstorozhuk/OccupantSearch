package com.occupantsearch.vk

import com.occupantsearch.db.Database
import com.occupantsearch.time.secondsToMillis
import com.vk.api.sdk.objects.wall.WallpostFull
import org.koin.core.component.KoinComponent
import java.util.Date
import java.util.concurrent.atomic.AtomicReference

class PostController(
    database: Database,
    private val vkNewsfeedSearcher: VkNewsfeedSearcher
) : KoinComponent {
    private val repository = database[WallpostFull::class.java]
    private val latestPostDate = AtomicReference<Date>(calculateLatestPostDate())

    fun getLatestPostDate() = latestPostDate.get()

    fun calculateLatestPostDate() = repository.getAll().values
        .maxOfOrNull { it.date }
        ?.secondsToMillis()
        ?.let { Date(it) }

    fun downloadNewPosts() = vkNewsfeedSearcher.search(getLatestPostDate()) { posts ->
        repository.saveAll(posts.map { it.uniqueId to it })
        latestPostDate.set(calculateLatestPostDate())
    }
}