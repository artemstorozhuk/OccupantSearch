package com.occupantsearch.io

import java.io.File

fun File.rewrite(text: String) {
    delete()
    createNewFile()
    writeText(text)
}
