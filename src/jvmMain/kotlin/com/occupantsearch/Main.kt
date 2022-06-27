package com.occupantsearch

import com.occupantsearch.koin.get
import com.occupantsearch.koin.initKoin
import com.occupantsearch.server.startServer
import com.occupantsearch.update.UpdateController

fun main() {
    initKoin()
    get<UpdateController>().start()
    startServer()
}