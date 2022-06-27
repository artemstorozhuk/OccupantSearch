package com.occupantsearch.server.routing

import com.occupantsearch.occupant.OccupantController
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.util.pipeline.PipelineContext
import org.koin.core.component.KoinComponent

class OccupantsResponder(
    private val occupantController: OccupantController
) : KoinComponent {
    suspend fun respond(pipeline: PipelineContext<Unit, ApplicationCall>) =
        pipeline.call.respond(
            occupantController.findOccupants(
                query = pipeline.context.parameters["query"] ?: "",
                page = pipeline.context.parameters["page"]?.toInt() ?: 0
            )
        )
}