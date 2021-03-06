package com.occupantsearch.server.routing

import com.occupantsearch.export.ExportController
import com.occupantsearch.export.Format
import com.occupantsearch.export.from
import com.occupantsearch.time.format
import com.occupantsearch.vk.PostDownloader
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.response.header
import io.ktor.server.response.respondText
import io.ktor.util.pipeline.PipelineContext
import org.koin.core.annotation.Single


@Single
class ExportResponder(
    private val exportController: ExportController,
    private val postDownloader: PostDownloader,
) {
    suspend fun respond(pipeline: PipelineContext<Unit, ApplicationCall>) {
        val format = pipeline.context.parameters["format"]?.lowercase()?.let { from(it) } ?: Format.JSON
        val date = postDownloader.getLatestPostDate().format()
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