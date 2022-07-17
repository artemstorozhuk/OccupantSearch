package com.occupantsearch.vk

import com.occupantsearch.lang.retryUntilSuccess
import com.occupantsearch.properties.PropertiesController
import com.occupantsearch.time.millisToSeconds
import com.occupantsearch.time.parseDate
import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.UserActor
import com.vk.api.sdk.exceptions.ApiTooManyException
import com.vk.api.sdk.httpclient.HttpTransportClient
import com.vk.api.sdk.objects.wall.WallpostFull
import org.koin.core.annotation.Single
import org.slf4j.LoggerFactory
import java.util.Date

@Single
class VkNewsfeedSearcher(
    props: PropertiesController
) {
    private val logger = LoggerFactory.getLogger(VkNewsfeedSearcher::class.java)
    private val vkClient = VkApiClient(HttpTransportClient())
    private val userActor = UserActor(props["vk"]["user_id"]!!.toInt(), props["vk"]["access_token"])
    private val timeDeltaSeconds = props["vk"]["time_delta"]!!.toInt()
    private val maxRecordsCount = props["vk"]["records_count"]!!.toInt()
    private val earliestDate = props["vk"]["earliest_date"]!!.parseDate()
    private val query = props["vk"]["query"]

    fun search(startDate: Date?, callback: (List<WallpostFull>) -> Unit) {
        val fromSeconds = (startDate ?: earliestDate).time.millisToSeconds()
        val toSeconds = Date().time.millisToSeconds()
        for (time in fromSeconds until toSeconds step timeDeltaSeconds) {
            retryUntilSuccess(lambda = {
                callback(
                    vkClient.newsfeed()
                        .search(userActor)
                        .q(query)
                        .count(maxRecordsCount)
                        .extended(true)
                        .startTime(time)
                        .endTime(time + timeDeltaSeconds)
                        .execute()
                        .items
                )
            }, onError = {
                if (it is ApiTooManyException) {
                    logger.info(it.message)
                } else {
                    logger.info(it.message, it)
                }
                true
            })
        }
    }
}