package com.occupantsearch.server.routing

import com.occupantsearch.resource.ResourceReader
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.util.pipeline.PipelineContext
import org.koin.core.component.KoinComponent

class IndexHtmlResponder(
    resourceReader: ResourceReader
) : KoinComponent {
    private val content = resourceReader.readResource("index.html")

    suspend fun respond(pipeline: PipelineContext<Unit, ApplicationCall>) = pipeline.call.respondText(
        text = content,
        contentType = ContentType.Text.Html
    )
}