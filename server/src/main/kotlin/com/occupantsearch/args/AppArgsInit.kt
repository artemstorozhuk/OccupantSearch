package com.occupantsearch.args

import com.xenomachina.argparser.ArgParser
import org.koin.core.annotation.Single

@Single
class AppArgsInit {
    lateinit var appArgs: AppArgs

    fun init(args: Array<String>) {
        appArgs = ArgParser(args).parseInto(::AppArgs)
    }
}