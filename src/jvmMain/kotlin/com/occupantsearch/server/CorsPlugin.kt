package com.occupantsearch.server

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.http.HttpMethod

fun Application.installCors() = install(CORS) {
    method(HttpMethod.Get)
    method(HttpMethod.Post)
    anyHost()
}