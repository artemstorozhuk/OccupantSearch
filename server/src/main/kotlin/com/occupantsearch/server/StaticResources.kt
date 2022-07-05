package com.occupantsearch.server

import io.ktor.server.http.content.resources
import io.ktor.server.http.content.static
import io.ktor.server.routing.Routing

fun Routing.installStatic() = static("/") { resources() }