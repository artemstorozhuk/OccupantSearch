package com.occupantsearch.occupant

import kotlinx.serialization.Serializable

@Serializable
data class OccupantsDto(
    val occupants: List<OccupantDto>,
    val foundCount: Int
)
