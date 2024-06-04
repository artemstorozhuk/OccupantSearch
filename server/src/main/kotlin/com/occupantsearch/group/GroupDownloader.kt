package com.occupantsearch.group

import com.occupantsearch.db.Database
import com.occupantsearch.group.author.AuthorFetcher
import com.occupantsearch.group.author.Ok
import com.occupantsearch.group.author.ParseError
import com.occupantsearch.group.author.TooManyRequests
import com.occupantsearch.group.author.Unavailable
import com.occupantsearch.vk.uniqueId
import com.vk.api.sdk.objects.wall.WallpostFull
import org.koin.core.annotation.Single
import org.slf4j.LoggerFactory
import java.util.stream.Collectors

@Single
class GroupDownloader(
    database: Database,
    private val authorFetcher: AuthorFetcher
) {
    private val logger = LoggerFactory.getLogger(GroupDownloader::class.java)
    private val postsRepository = database.load(WallpostFull::class.java)
    private val groupRepository = database.load(Group::class.java)

    fun downloadNewGroups() {
        val newGroups = getNewGroups()
        logger.info("Groups total: ${groupRepository.getAll().size}, to download: ${newGroups.size}")
        downloadGroups(newGroups)
    }

    fun getNewGroups(): Map<Int, List<WallpostFull>> = postsRepository.getAll()
        .values
        .stream()
        .parallel()
        .filter { it.ownerId.toString() !in groupRepository.getAll().keys }
        .collect(Collectors.groupingByConcurrent { it.ownerId })

    fun downloadGroups(groups: Map<Int, List<WallpostFull>>) =
        groups.values
            .stream()
            .parallel()
            .forEach { posts ->
                downloadAuthor(posts).let { result ->
                    when (result) {
                        is Ok -> groupRepository[posts.ownerId.toString()] = Group(
                            ownerId = posts.ownerId,
                            name = result.author.name,
                            url = result.author.url,
                            avatar = result.author.avatar
                        ).also { logger.info("Downloaded group ${posts.ownerId}") }
                        is Unavailable -> groupRepository[posts.ownerId.toString()] = Group(ownerId = posts.ownerId)
                        is ParseError -> logger.info("Parse error: ${result.text}")
                        is TooManyRequests -> logger.info("Too many requests: ${posts.first().uniqueId}")
                    }
                }
            }

    fun downloadAuthor(posts: List<WallpostFull>) =
        posts.firstNotNullOf {
            authorFetcher.fetch(it.uniqueId)
        }

    val List<WallpostFull>.ownerId get() = first().ownerId
}