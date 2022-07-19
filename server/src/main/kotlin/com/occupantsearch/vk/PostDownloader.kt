package com.occupantsearch.vk

import com.occupantsearch.db.Database
import com.occupantsearch.time.secondsToMillis
import com.vk.api.sdk.objects.wall.WallpostFull
import org.koin.core.annotation.Single
import java.util.Date
import java.util.concurrent.atomic.AtomicReference

@Single
class PostDownloader(
    database: Database,
    private val vkNewsfeedSearcher: VkNewsfeedSearcher,
) {
    private val repository = database.load(WallpostFull::class.java)
    private val latestPostDate = AtomicReference<Date>(calculateLatestPostDate())

    fun getLatestPostDate(): Date = latestPostDate.get()

    fun calculateLatestPostDate() = repository.getAll().values
        .maxOfOrNull { it.date }
        ?.secondsToMillis()
        ?.let { Date(it) }

    fun downloadNewPosts() = vkNewsfeedSearcher.search(getLatestPostDate()) { posts ->
        repository.saveAll(posts.map { it.uniqueId to it })
        latestPostDate.set(calculateLatestPostDate())
    }
}