package com.occupantsearch.server.routing

import com.occupantsearch.analytics.AnalyticsController
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.util.pipeline.PipelineContext
import org.koin.core.component.KoinComponent

class AnalyticsResponder(
    private val analyticsController: AnalyticsController
) : KoinComponent {

    suspend fun respond(pipeline: PipelineContext<Unit, ApplicationCall>) =
        pipeline.call.respond(
            analyticsController.getAnalytics()
        )
}