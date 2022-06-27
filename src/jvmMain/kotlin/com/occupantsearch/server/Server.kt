package com.occupantsearch.server

import com.occupantsearch.properties.getProperty
import com.occupantsearch.server.plugins.installCors
import com.occupantsearch.server.plugins.installJson
import com.occupantsearch.server.plugins.installLog
import com.occupantsearch.server.plugins.installZip
import com.occupantsearch.server.routing.ExportResponder
import com.occupantsearch.server.routing.IndexHtmlResponder
import com.occupantsearch.server.routing.OccupantsResponder
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.koin.core.component.KoinComponent

class Server(
    private val indexHtmlResponder: IndexHtmlResponder,
    private val occupantsResponder: OccupantsResponder,
    private val exportResponder: ExportResponder,
) : KoinComponent {
    fun start() = embeddedServer(
        factory = Netty,
        host = getProperty("server", "host"),
        port = getProperty("server", "port").toInt()
    ) {
        installJson()
        installCors()
        installLog()
        installZip()
        routing {
            get("/") { indexHtmlResponder.respond(this) }
            get("/occupants") { occupantsResponder.respond(this) }
            get("/export") { exportResponder.respond(this) }
            installStatic()
        }
    }.start(wait = true)
}