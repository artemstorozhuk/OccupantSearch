package com.occupantsearch.server.routing

import com.occupantsearch.resource.ResourceReader
import io.ktor.http.ContentType
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.util.pipeline.PipelineContext
import org.koin.core.annotation.Single

@Single
class IndexHtmlResponder(
    resourceReader: ResourceReader
) {
    private val content = resourceReader.readResource("app/index.html")

    suspend fun respond(pipeline: PipelineContext<Unit, ApplicationCall>) = pipeline.call.respondText(
        text = content,
        contentType = ContentType.Text.Html
    )
}