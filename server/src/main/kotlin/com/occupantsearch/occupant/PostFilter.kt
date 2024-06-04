package com.occupantsearch.occupant

import com.occupantsearch.resource.ResourceReader
import com.vk.api.sdk.objects.wall.WallpostFull
import org.koin.core.annotation.Single

@Single
class PostFilter(
    resourceReader: ResourceReader
) {
    private val phrases = resourceReader.readResourceAsSet("/post_marker_phrases.txt")

    fun filter(post: WallpostFull) = post.text.lowercase().let { lowercase ->
        phrases.any { lowercase.contains(it) }
    }
}
