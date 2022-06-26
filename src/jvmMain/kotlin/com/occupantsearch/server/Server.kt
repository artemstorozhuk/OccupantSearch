package com.occupantsearch.server

import com.occupantsearch.occupant.OccupantController
import com.occupantsearch.resource.ResourceReader
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.koin.java.KoinJavaComponent.getKoin

fun startServer() = embeddedServer(Netty, port = 8080, host = "localhost") {
    installJson()
    installCors()
    installLog()
    installZip()
    val indexHtml = getKoin().get<ResourceReader>().readResource("index.html")
    routing {
        get("/") {
            call.respondText(
                text = indexHtml,
                contentType = ContentType.Text.Html
            )
        }
        get("/occupants") {
            call.respond(
                getKoin().get<OccupantController>()
                    .findOccupants(
                        query = context.parameters["query"] ?: "",
                        page = context.parameters["page"]?.toInt() ?: 0
                    )
            )
        }
        static("/static") {
            resources()
        }
    }
}.start(wait = true)