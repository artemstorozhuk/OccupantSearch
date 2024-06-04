package com.occupantsearch.server.routing

import com.occupantsearch.group.GroupController
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.util.pipeline.PipelineContext
import org.koin.core.annotation.Single

@Single
class GroupResponder(
    private val groupController: GroupController,
) {
    suspend fun respond(pipeline: PipelineContext<Unit, ApplicationCall>) =
        pipeline.call.respond(
            groupController.getGroupsCount(
                page = pipeline.context.parameters["page"]?.toInt() ?: 0
            )
        )
}