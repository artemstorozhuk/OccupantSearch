package com.occupantsearch.occupant

import com.occupantsearch.person.Person

data class Occupant(
    val person: Person,
    val postIds: List<String>,
    val faceImageUrls: List<String>,
    val date: Int,
)