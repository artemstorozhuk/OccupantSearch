package com.occupantsearch.server.routing

import com.occupantsearch.analytics.Analytics
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.util.pipeline.PipelineContext
import org.koin.core.annotation.Single

@Single
class Analytics(
    private val analytics: Analytics
) {

    suspend fun respond(pipeline: PipelineContext<Unit, ApplicationCall>) =
        pipeline.call.respond(
            analytics.getAnalytics()
        )
}