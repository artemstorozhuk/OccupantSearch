package com.occupantsearch.server.plugins

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.serialization.json

fun Application.installJson() = install(ContentNegotiation) { json() }