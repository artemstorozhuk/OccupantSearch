package com.occupantsearch.server.routing

import com.occupantsearch.export.ExportController
import com.occupantsearch.export.Format
import com.occupantsearch.export.from
import com.occupantsearch.time.format
import com.occupantsearch.vk.PostController
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.response.header
import io.ktor.response.respondText
import io.ktor.util.pipeline.PipelineContext
import org.koin.core.component.KoinComponent


class ExportResponder(
    private val exportController: ExportController,
    private val postController: PostController
) : KoinComponent {
    suspend fun respond(pipeline: PipelineContext<Unit, ApplicationCall>) {
        val format = pipeline.context.parameters["format"]?.lowercase()?.let { from(it) } ?: Format.JSON
        val date = postController.getLatestPostDate()?.format() ?: ""
        pipeline.call.response.header(
            name = HttpHeaders.ContentDisposition,
            value = "attachment; filename=\"occupants-$date.${format.value}\""
        )
        pipeline.call.respondText(
            contentType = ContentType.Application.OctetStream,
            status = HttpStatusCode.OK,
            text = exportController.export(format)
        )
    }
}