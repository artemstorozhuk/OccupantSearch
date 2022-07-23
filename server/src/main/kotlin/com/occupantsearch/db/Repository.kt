package com.occupantsearch.db

import com.occupantsearch.concurrent.ValueLock
import com.occupantsearch.io.rewrite
import com.occupantsearch.json.parseJson
import com.occupantsearch.json.toJson
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
    private val lock = ValueLock()

    fun load() {
        root.mkdirs()
        measureDuration {
            Arrays.stream(root.listFiles())
                .parallel()
                .forEach { file ->
                    if (file != null) {
                        val key = file.nameWithoutExtension
                        lock(key) {
                            data[key] = file.readText().parseJson(clazz)
                        }
                    }
                }
        }.let { logger.info("Repository ${clazz.canonicalName} loaded in ${it.duration}") }
    }

    fun unload() = data.clear()

    fun withData(lambda: (Map<String, T>) -> Unit) {
        load()
        lambda(data)
        unload()
    }

    operator fun set(key: String, value: T) =
        if (value == null) {
            delete(key)
        } else {
            lock(key) {
                getFile(key).rewrite(value.toJson())
                data[key] = value
            }
        }

    fun saveAll(pairs: Collection<Pair<String, T>>) =
        pairs.stream()
            .parallel()
            .forEach { this[it.first] = it.second }

    fun deleteAll(keys: Collection<String>) =
        keys.stream()
            .parallel()
            .forEach { delete(it) }

    fun delete(key: String) = lock(key) {
        getFile(key).delete()
        data.remove(key)
    }

    operator fun get(key: String) = data[key]

    fun getAll(): Map<String, T> = data

    fun lock(key: String, lambda: () -> Unit) = lock.lock(key, lambda)

    fun getFile(key: String) = File(root, "$key.json")
}