package com.occupantsearch.server.routing


import com.occupantsearch.occupant.OccupantController
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.util.getOrFail
import io.ktor.util.pipeline.PipelineContext
import org.koin.core.component.KoinComponent

class OccupantResponder(
    private val occupantController: OccupantController
) : KoinComponent {
    suspend fun respond(pipeline: PipelineContext<Unit, ApplicationCall>) {
        val name = pipeline.call.parameters.getOrFail<String>("name")
        val posts = occupantController.findPosts(name)
        if (posts != null) {
            pipeline.call.respond(posts)
        } else {
            pipeline.call.respond(HttpStatusCode.BadRequest)
        }
    }
}