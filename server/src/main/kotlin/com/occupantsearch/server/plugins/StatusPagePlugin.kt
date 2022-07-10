package com.occupantsearch.server.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respondRedirect

fun Application.installStatusPage() = install(StatusPages) {
    status(HttpStatusCode.NotFound) { call, _ ->
        call.respondRedirect("/")
    }
}