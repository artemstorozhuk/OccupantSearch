package com.occupantsearch.analytics

import kotlinx.serialization.Serializable

@Serializable
data class AnalyticsDto(
    val postsCountByDate: Map<Int, Long>,
    val occupantsCountByDate: Map<Int, Long>,
)
