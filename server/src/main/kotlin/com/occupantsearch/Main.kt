package com.occupantsearch

import com.occupantsearch.args.AppArgsController
import com.occupantsearch.koin.initKoin
import com.occupantsearch.server.Server
import com.occupantsearch.update.UpdateController

fun main(args: Array<String>) {
    val koin = initKoin()
    koin.get<AppArgsController>().init(args)
    koin.get<UpdateController>().start()
    koin.get<Server>().start()
}