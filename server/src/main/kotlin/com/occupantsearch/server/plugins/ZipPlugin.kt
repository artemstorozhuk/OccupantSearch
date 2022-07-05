package com.occupantsearch.server.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.compression.Compression
import io.ktor.server.plugins.compression.gzip

fun Application.installZip() = install(Compression) { gzip() }