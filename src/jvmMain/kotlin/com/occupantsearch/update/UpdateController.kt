package com.occupantsearch.update

import com.occupantsearch.image.ImageFaceController
import com.occupantsearch.occupant.OccupantController
import com.occupantsearch.properties.PropertiesController
import com.occupantsearch.time.measureDuration
import com.occupantsearch.vk.PostController
import org.slf4j.LoggerFactory
import java.util.Timer
import java.util.TimerTask

class UpdateController(
    props: PropertiesController,
    private val occupantController: OccupantController,
    private val postController: PostController,
    private val imageFaceController: ImageFaceController
) {
    private val logger = LoggerFactory.getLogger(UpdateController::class.java)
    private val updateTime = props["server"]["update_time"]!!.toLong()

    fun start() {
        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val duration = measureDuration {
                    postController.downloadNewPosts()
                    occupantController.refresh()
                    imageFaceController.refresh()
                }
                logger.info("Updated state in $duration")
            }
        }, 0, updateTime)
    }
}