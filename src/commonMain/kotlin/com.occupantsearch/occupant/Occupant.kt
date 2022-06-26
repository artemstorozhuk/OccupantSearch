package com.occupantsearch.occupant

import com.occupantsearch.person.Person
import kotlinx.serialization.Serializable

@Serializable
data class Occupant(
    val person: Person,
    val postIds: List<String>,
    val faceImageUrls: List<String>,
)