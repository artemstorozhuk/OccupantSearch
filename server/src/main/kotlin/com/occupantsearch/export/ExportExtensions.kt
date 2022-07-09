package com.occupantsearch.export

import com.occupantsearch.occupant.Occupant
import com.occupantsearch.vk.postUrl

fun Occupant.asCsv() = """
            ${person.firstname}, ${person.lastname}, ${postIds.joinToString(", ") { it.postUrl }}
        """.trimIndent()

fun Occupant.asJson() = mapOf(
    "name" to person.fullName,
    "posts" to postIds.map { it.postUrl },
    "images" to faceImageUrls
)