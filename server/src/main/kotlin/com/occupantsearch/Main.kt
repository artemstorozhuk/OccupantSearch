package com.occupantsearch

import com.occupantsearch.args.AppArgsController
import com.occupantsearch.koin.OccupantSearch
import com.occupantsearch.server.Server
import com.occupantsearch.update.UpdateController
import org.koin.core.context.GlobalContext.startKoin
import org.koin.ksp.generated.module

fun main(args: Array<String>) {
    val koin = startKoin {
        modules(OccupantSearch().module)
    }.koin

    koin.get<AppArgsController>().init(args)
    koin.get<UpdateController>().start()
    koin.get<Server>().start()
}