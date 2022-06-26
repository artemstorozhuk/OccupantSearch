package com.occupantsearch.io

import java.io.InputStream

fun InputStream.readText(): String =
    bufferedReader().use { it.readText() }