package com.occupantsearch.server

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.Compression
import io.ktor.features.gzip

fun Application.installZip() = install(Compression) { gzip() }