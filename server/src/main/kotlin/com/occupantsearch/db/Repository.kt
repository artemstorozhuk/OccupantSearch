package com.occupantsearch.db

import com.occupantsearch.io.rewrite
import com.occupantsearch.json.parseJson
import com.occupantsearch.json.toJson
import com.occupantsearch.lang.toMap
import com.occupantsearch.time.measureDuration
import org.slf4j.LoggerFactory
import java.io.File
import java.util.Arrays
import java.util.concurrent.ConcurrentHashMap

class Repository<T>(
    private val clazz: Class<T>,
    location: String
) {
    private val logger = LoggerFactory.getLogger(Repository::class.java)
    private val root = File("$location${clazz.canonicalName}")
    private val data = ConcurrentHashMap<String, T>()

    fun load() {
        root.mkdirs()
        measureDuration {
            data.putAll(Arrays.stream(root.listFiles()!!).parallel()
                .map { it.nameWithoutExtension to it.readText().parseJson(clazz) }
                .filter { it.second != null }
                .toMap()
            )
        }.let { duration -> logger.info("Repository ${clazz.canonicalName} loaded in $duration") }
    }

    operator fun set(key: String, value: T) {
        if (value == null) {
            File(root, "$key.json").delete()
            data.remove(key)
        } else {
            File(root, "$key.json").rewrite(value.toJson())
            data[key] = value
        }
    }

    fun saveAll(pairs: Collection<Pair<String, T>>) =
        pairs.stream()
            .parallel()
            .forEach { this[it.first] = it.second }

    operator fun get(key: String) = data[key]

    fun getAll(): Map<String, T> = data
}