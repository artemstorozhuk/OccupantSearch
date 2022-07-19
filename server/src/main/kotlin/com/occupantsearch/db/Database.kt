package com.occupantsearch.db

import com.occupantsearch.properties.PropertiesController
import org.koin.core.annotation.Single
import java.util.concurrent.ConcurrentHashMap

@Single
class Database(
    props: PropertiesController
) {
    private val repositories = ConcurrentHashMap<Class<*>, Repository<*>>()
    private val location = props["db"]["location"]!!

    operator fun <T> get(clazz: Class<T>) = load(clazz) {}

    fun <T> load(clazz: Class<T>, lambda: (Repository<T>) -> Unit = { it.load() }): Repository<T> =
        repositories.computeIfAbsent(clazz) {
            Repository(clazz, location).also { lambda(it) }
        } as Repository<T>
}