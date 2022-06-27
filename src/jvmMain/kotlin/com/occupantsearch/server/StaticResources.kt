package com.occupantsearch.server

import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.routing.Routing

fun Routing.installStatic() = static("/static") { resources() }