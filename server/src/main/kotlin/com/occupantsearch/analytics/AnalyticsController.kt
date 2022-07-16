package com.occupantsearch.analytics

import org.koin.core.component.KoinComponent

class AnalyticsController(
    private val occupantsCountByDateController: OccupantsCountByDateController,
    private val postsCountByDateController: PostsCountByDateController,
) : KoinComponent {
    fun refresh() {
        occupantsCountByDateController.refresh()
        postsCountByDateController.refresh()
    }

    fun getAnalytics() = AnalyticsDto(
        postsCountByDate = postsCountByDateController.getPostsCountByDate(),
        occupantsCountByDate = occupantsCountByDateController.getOccupantsCountByDate(),
    )
}