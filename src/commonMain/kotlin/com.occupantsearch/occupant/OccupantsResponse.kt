package com.occupantsearch.occupant

import kotlinx.serialization.Serializable

@Serializable
data class OccupantsResponse(
    val occupants: List<Occupant>,
    val foundCount: Int
)
