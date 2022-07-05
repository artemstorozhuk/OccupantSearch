package com.occupantsearch.properties

import com.occupantsearch.resource.ResourceReader
import java.io.StringReader
import java.util.Properties
import java.util.concurrent.ConcurrentHashMap

class PropertiesController(
    private val resourceReader: ResourceReader
) {
    private val files = ConcurrentHashMap<String, Map<String, String>>()

    operator fun get(file: String): Map<String, String> =
        files.computeIfAbsent(file) {
            Properties().let { properties ->
                resourceReader.readResource("$file.properties")
                    .let { properties.load(StringReader(it)) }
                properties.map { it.key.toString() to it.value.toString() }
                    .toMap()
            }
        }
}