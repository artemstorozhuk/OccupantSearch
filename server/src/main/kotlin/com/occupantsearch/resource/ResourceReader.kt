package com.occupantsearch.resource

import com.occupantsearch.io.readText

class ResourceReader {
    fun readResource(resource: String) =
        this::class.java.classLoader.getResourceAsStream(resource)!!.readText()

    fun readResourceAsSet(resource: String, filter: (String) -> Boolean = { true }) = ResourceReader::class.java
        .getResourceAsStream(resource)!!
        .readText()
        .split("\n")
        .filter { it.isNotBlank() }
        .filter { filter(it) }
        .toSet()
}