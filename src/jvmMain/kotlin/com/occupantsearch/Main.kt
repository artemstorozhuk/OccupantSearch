package com.occupantsearch

import com.occupantsearch.koin.initKoin
import com.occupantsearch.server.startServer
import com.occupantsearch.update.UpdateController
import org.koin.java.KoinJavaComponent.getKoin

fun main() {
    initKoin()
    getKoin().get<UpdateController>().start()
    startServer()
}