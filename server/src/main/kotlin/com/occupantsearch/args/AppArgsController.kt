package com.occupantsearch.args

import com.xenomachina.argparser.ArgParser
import org.koin.core.component.KoinComponent

class AppArgsController : KoinComponent {
    lateinit var appArgs: AppArgs

    fun init(args: Array<String>) {
        appArgs = ArgParser(args).parseInto(::AppArgs)
    }
}