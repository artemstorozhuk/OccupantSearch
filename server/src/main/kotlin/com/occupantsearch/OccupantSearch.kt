package com.occupantsearch

import com.occupantsearch.args.AppArgsInit
import com.occupantsearch.server.Server
import com.occupantsearch.update.UpdateController
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.context.GlobalContext.startKoin
import org.koin.ksp.generated.module

@Module
@ComponentScan("com.occupantsearch")
object OccupantSearch {
    @JvmStatic
    fun main(args: Array<String>): Unit =
        startKoin { modules(module) }.koin.run {
            get<AppArgsInit>().init(args)
            get<UpdateController>().start()
            get<Server>().start()
        }
}