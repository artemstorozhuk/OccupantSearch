package com.occupantsearch.server

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.request.httpMethod
import io.ktor.request.path
import org.slf4j.event.Level

fun Application.installLog() = install(CallLogging) {
    level = Level.INFO
    filter { call ->
        call.request.path().startsWith("/api/v1")
    }
    format { call ->
        val status = call.response.status()
        val httpMethod = call.request.httpMethod.value
        val userAgent = call.request.headers["User-Agent"]
        "Status: $status, HTTP method: $httpMethod, User agent: $userAgent"
    }
}