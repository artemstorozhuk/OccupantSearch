package com.occupantsearch.server

import com.occupantsearch.koin.get
import com.occupantsearch.occupant.OccupantController
import com.occupantsearch.properties.getProperty
import com.occupantsearch.resource.ResourceReader
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun startServer() = embeddedServer(
    factory = Netty,
    host = getProperty("server", "host"),
    port = getProperty("server", "port").toInt()
) {
    installJson()
    installCors()
    installLog()
    installZip()
    val indexHtml = get<ResourceReader>().readResource("index.html")
    routing {
        get("/") {
            call.respondText(
                text = indexHtml,
                contentType = ContentType.Text.Html
            )
        }
        get("/occupants") {
            call.respond(
                get<OccupantController>()
                    .findOccupants(
                        query = context.parameters["query"] ?: "",
                        page = context.parameters["page"]?.toInt() ?: 0
                    )
            )
        }
        installStatic()
    }
}.start(wait = true)