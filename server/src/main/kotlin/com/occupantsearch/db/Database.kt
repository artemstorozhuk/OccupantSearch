package com.occupantsearch.db

import com.occupantsearch.properties.PropertiesController
import java.util.concurrent.ConcurrentHashMap

class Database(
    props: PropertiesController
) {
    private val repositories = ConcurrentHashMap<Class<*>, Repository<*>>()
    private val location = props["db"]["location"]!!

    operator fun <T> get(clazz: Class<T>): Repository<T> =
        repositories.computeIfAbsent(clazz) {
            Repository(clazz, location).also { it.load() }
        } as Repository<T>
}