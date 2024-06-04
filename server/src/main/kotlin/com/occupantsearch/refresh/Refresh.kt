package com.occupantsearch.refresh

import com.occupantsearch.analytics.Analytics
import com.occupantsearch.db.Database
import com.occupantsearch.group.GroupController
import com.occupantsearch.group.GroupDownloader
import com.occupantsearch.image.FaceDetections
import com.occupantsearch.image.ImageFaceController
import com.occupantsearch.natasha.NatashaProcessor
import com.occupantsearch.occupant.OccupantController
import com.occupantsearch.properties.PropertiesController
import com.occupantsearch.time.measureDuration
import com.occupantsearch.vk.PostDownloader
import org.koin.core.annotation.Single
import org.slf4j.LoggerFactory
import java.util.Timer
import java.util.TimerTask

@Single
class Refresh(
    props: PropertiesController,
    database: Database,
    private val natashaController: NatashaProcessor,
    private val occupantController: OccupantController,
    private val postDownloader: PostDownloader,
    private val imageFaceController: ImageFaceController,
    private val analyticsController: Analytics,
    private val groupDownloader: GroupDownloader,
    private val groupController: GroupController,
) {
    private val logger = LoggerFactory.getLogger(Refresh::class.java)
    private val faceDetectionsRepository = database[FaceDetections::class.java]
    private val updateTime = props["server"]["update_time"]!!.toLong()

    fun startRefresh() = Timer().scheduleAtFixedRate(object : TimerTask() {
        override fun run() = measureDuration {
            postDownloader.downloadNewPosts()
            faceDetectionsRepository.withData {
                imageFaceController.processNewPosts()
                occupantController.update()
            }
            natashaController.processNewPosts()
            analyticsController.update()
            groupDownloader.downloadNewGroups()
            groupController.update()
        }.let { logger.info("Updated state in ${it.duration}") }
    }, 0, updateTime)
}