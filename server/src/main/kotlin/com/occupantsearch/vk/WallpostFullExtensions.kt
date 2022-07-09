package com.occupantsearch.vk

import com.vk.api.sdk.objects.wall.WallpostFull
import java.io.File
import java.net.URI

val WallpostFull.uniqueId
    get() = "${ownerId}_${id}"

fun WallpostFull.getImageUris(): List<URI> = attachments
    ?.filter { it.photo?.sizes?.isNotEmpty() ?: false }
    ?.map { it.photo.sizes.maxByOrNull { size -> size.height * size.width }!!.url }
    ?: listOf()

fun WallpostFull.getImageId(uri: URI) = "${uniqueId}_${File(uri.path).name}"

fun WallpostFull.toDto() = PostDto(
    postId = uniqueId,
    text = text,
    imageUrls = getImageUris().map { it.toString() },
    views = views?.count,
    date = date
)