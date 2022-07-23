package com.occupantsearch.server.routing

import com.occupantsearch.geo.LocationController
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.util.pipeline.PipelineContext
import org.koin.core.annotation.Single

@Single
class MapResponder(
    private val locationController: LocationController
) {
    suspend fun respond(pipeline: PipelineContext<Unit, ApplicationCall>) {
        val zoom = pipeline.context.parameters["zoom"]?.toInt() ?: 0
        val latitude = pipeline.context.parameters["latitude"]?.toDouble() ?: 0.0
        val longitude = pipeline.context.parameters["longitude"]?.toDouble() ?: 0.0
        pipeline.call.respond(
            locationController.get(
                latitude = latitude,
                longitude = longitude,
                zoom = zoom
            )
        )
    }
}