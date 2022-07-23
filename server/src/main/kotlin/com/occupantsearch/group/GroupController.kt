package com.occupantsearch.group

import com.occupantsearch.db.Database
import com.occupantsearch.properties.PropertiesController
import com.vk.api.sdk.objects.wall.WallpostFull
import org.koin.core.annotation.Single
import java.util.concurrent.atomic.AtomicReference
import java.util.stream.Collectors

@Single
class GroupController(
    database: Database,
    propertiesController: PropertiesController,
) {
    private val postsRepository = database.load(WallpostFull::class.java)
    private val groupRepository = database.load(Group::class.java)
    private val groupsCountReference = AtomicReference<List<Pair<Int, Long>>>(listOf())
    private val pageSize = propertiesController["server"]["group_page_size"]!!.toInt()

    fun refresh() = groupsCountReference.set(
        postsRepository.getAll()
            .values
            .stream()
            .parallel()
            .collect(Collectors.groupingByConcurrent({ it.ownerId }, Collectors.counting()))
            .entries
            .stream()
            .parallel()
            .filter { groupRepository[it.key.toString()]?.hasInformation() ?: false }
            .map { Pair(it.key, it.value) }
            .sorted { a, b -> (b.second - a.second).toInt() }
            .collect(Collectors.toList())
    )

    fun getGroupsCount(page: Int): List<GroupDto> = groupsCountReference.get()
        .let { list ->
            list.subList(
                fromIndex = minOf(list.size, page * pageSize),
                toIndex = minOf(list.size, (page + 1) * pageSize)
            ).map {
                val group = groupRepository[it.first.toString()]!!
                GroupDto(
                    name = group.name!!,
                    count = it.second,
                    url = group.url!!,
                    avatar = group.avatar!!
                )
            }
        }
}