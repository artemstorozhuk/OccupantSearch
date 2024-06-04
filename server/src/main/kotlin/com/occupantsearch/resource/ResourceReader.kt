package com.occupantsearch.resource

import com.occupantsearch.io.readText
import org.koin.core.annotation.Single

@Single
class ResourceReader {
    fun readResource(resource: String) =
        this::class.java.classLoader.getResourceAsStream(resource)!!.readText()

    fun readResourceAsSet(resource: String) = ResourceReader::class.java
        .getResourceAsStream(resource)!!
        .readText()
        .replace("\r", "")
        .split("\n")
        .filter { it.isNotBlank() }
        .toSet()
}