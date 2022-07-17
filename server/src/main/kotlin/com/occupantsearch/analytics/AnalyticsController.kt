package com.occupantsearch.analytics

import org.koin.core.annotation.Single

@Single
class AnalyticsController(
    private val occupantsCountByDateController: OccupantsCountByDateController,
    private val postsCountByDateController: PostsCountByDateController,
) {
    fun refresh() {
        occupantsCountByDateController.refresh()
        postsCountByDateController.refresh()
    }

    fun getAnalytics() = AnalyticsDto(
        postsCountByDate = postsCountByDateController.getPostsCountByDate(),
        occupantsCountByDate = occupantsCountByDateController.getOccupantsCountByDate(),
    )
}