package com.occupantsearch.server.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.callloging.CallLogging
import org.slf4j.event.Level

fun Application.installLog() = install(CallLogging) {
    level = Level.INFO
    filter { false }
}