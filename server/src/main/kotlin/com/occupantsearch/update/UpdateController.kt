package com.occupantsearch.update

import com.occupantsearch.analytics.AnalyticsController
import com.occupantsearch.group.GroupController
import com.occupantsearch.group.GroupDownloader
import com.occupantsearch.image.ImageFaceController
import com.occupantsearch.occupant.OccupantController
import com.occupantsearch.properties.PropertiesController
import com.occupantsearch.time.measureDuration
import com.occupantsearch.vk.PostDownloader
import org.koin.core.annotation.Single
import org.slf4j.LoggerFactory
import java.util.Timer
import java.util.TimerTask

@Single
class UpdateController(
    props: PropertiesController,
    private val occupantController: OccupantController,
    private val postDownloader: PostDownloader,
    private val imageFaceController: ImageFaceController,
    private val analyticsController: AnalyticsController,
    private val groupDownloader: GroupDownloader,
    private val groupController: GroupController,
) {
    private val logger = LoggerFactory.getLogger(UpdateController::class.java)
    private val updateTime = props["server"]["update_time"]!!.toLong()

    fun start() = Timer().scheduleAtFixedRate(object : TimerTask() {
        override fun run() = measureDuration {
            postDownloader.downloadNewPosts()
            occupantController.refresh()
            imageFaceController.refresh()
            analyticsController.refresh()
            groupDownloader.downloadNewGroups()
            groupController.refresh()
        }.let { duration -> logger.info("Updated state in $duration") }
    }, 0, updateTime)
}