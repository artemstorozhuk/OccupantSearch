package com.occupantsearch.analytics

import org.koin.core.annotation.Single

@Single
class Analytics(
    private val occupantsCountByDate: OccupantsCountByDate,
    private val postsCountByDate: PostsCountByDate,
) {
    fun update() {
        occupantsCountByDate.update()
        postsCountByDate.update()
    }

    fun getAnalytics() = AnalyticsDto(
        postsCountByDate = postsCountByDate.getPostsCountByDate(),
        occupantsCountByDate = occupantsCountByDate.getOccupantsCountByDate(),
    )
}