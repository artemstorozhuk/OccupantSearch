package com.occupantsearch.args

import com.xenomachina.argparser.ArgParser


class AppArgs(parser: ArgParser) {
    val enableCors by parser.flagging(
        "--enable-cors",
        help = "enable CORS"
    )
}