package com.occupantsearch.server

import com.occupantsearch.args.AppArgsController
import com.occupantsearch.server.plugins.installCors
import com.occupantsearch.server.plugins.installJson
import com.occupantsearch.server.plugins.installStatusPage
import com.occupantsearch.server.plugins.installZip
import com.occupantsearch.server.routing.AnalyticsResponder
import com.occupantsearch.server.routing.ExportResponder
import com.occupantsearch.server.routing.GroupResponder
import com.occupantsearch.server.routing.IndexHtmlResponder
import com.occupantsearch.server.routing.MapResponder
import com.occupantsearch.server.routing.OccupantResponder
import com.occupantsearch.server.routing.OccupantsResponder
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import org.koin.core.annotation.Single

@Single
class Server(
    private val indexHtmlResponder: IndexHtmlResponder,
    private val occupantsResponder: OccupantsResponder,
    private val occupantResponder: OccupantResponder,
    private val exportResponder: ExportResponder,
    private val analyticsResponder: AnalyticsResponder,
    private val appArgsController: AppArgsController,
    private val groupResponder: GroupResponder,
    private val mapResponder: MapResponder,
) {
    fun start() = embeddedServer(
        factory = Netty,
        port = 8080
    ) {
        installJson()
        if (appArgsController.appArgs.enableCors) {
            installCors()
        }
        installZip()
        installStatusPage()
        routing {
            get("/") { indexHtmlResponder.respond(this) }
            route("api") {
                route("v1") {
                    get("occupants") { occupantsResponder.respond(this) }
                    get("occupant/{name}") { occupantResponder.respond(this) }
                    get("export") { exportResponder.respond(this) }
                    get("analytics") { analyticsResponder.respond(this) }
                    get("groups") { groupResponder.respond(this) }
                    get("map") { mapResponder.respond(this) }
                }
            }
            installStatic()
        }
    }.start(wait = true)
}