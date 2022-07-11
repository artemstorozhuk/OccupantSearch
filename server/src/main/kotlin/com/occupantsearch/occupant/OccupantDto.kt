package com.occupantsearch.occupant

import kotlinx.serialization.Serializable

@Serializable
data class OccupantDto(
    val name: String,
    val imageUrl: String?,
    val date: Int
)

fun Occupant.toDto() = OccupantDto(
    name = person.fullName,
    imageUrl = faceImageUrls.firstOrNull(),
    date = date,
)