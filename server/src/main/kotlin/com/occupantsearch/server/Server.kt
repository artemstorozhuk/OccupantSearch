package com.occupantsearch.server

import com.occupantsearch.args.AppArgsController
import com.occupantsearch.server.plugins.installCors
import com.occupantsearch.server.plugins.installJson
import com.occupantsearch.server.plugins.installZip
import com.occupantsearch.server.routing.AnalyticsResponder
import com.occupantsearch.server.routing.ExportResponder
import com.occupantsearch.server.routing.IndexHtmlResponder
import com.occupantsearch.server.routing.OccupantsResponder
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.koin.core.component.KoinComponent

class Server(
    private val indexHtmlResponder: IndexHtmlResponder,
    private val occupantsResponder: OccupantsResponder,
    private val exportResponder: ExportResponder,
    private val analyticsResponder: AnalyticsResponder,
    private val appArgsController: AppArgsController
) : KoinComponent {
    fun start() = embeddedServer(
        factory = Netty,
        port = 8080
    ) {
        installJson()
        if (appArgsController.appArgs.enableCors) {
            installCors()
        }
        installZip()
        routing {
            get("/") { indexHtmlResponder.respond(this) }
            get("/occupants") { occupantsResponder.respond(this) }
            get("/export") { exportResponder.respond(this) }
            get("/analytics") { analyticsResponder.respond(this) }
            installStatic()
        }
    }.start(wait = true)
}