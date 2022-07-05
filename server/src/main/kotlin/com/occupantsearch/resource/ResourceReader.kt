package com.occupantsearch.resource

import com.occupantsearch.io.readText

class ResourceReader {
    fun readResource(resource: String) =
        this::class.java.classLoader.getResourceAsStream(resource)!!.readText()

    fun readResourceAsSet(resource: String) = ResourceReader::class.java
        .getResourceAsStream(resource)!!
        .readText()
        .split("\n")
        .filter { it.first().isUpperCase() }
        .filter { it.isNotBlank() }
        .toSet()
}