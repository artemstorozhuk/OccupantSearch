package com.occupantsearch.geo

import com.occupantsearch.db.Database
import org.koin.core.annotation.Single
import org.slf4j.LoggerFactory
import java.io.File

// http://download.geonames.org/export/dump/
@Single
class GeoNamesParser(
    database: Database
) {
    private val logger = LoggerFactory.getLogger(GeoNamesParser::class.java)
    private val repository = database.load(Locations::class.java)

    fun parse(input: String) = File(input)
        .readText()
        .replace("\r", "")
        .split("\n")
        .map { it.split("\t") }
        .filter {
            it.size == 19
                && it[6] == "P"
                && it[3].isNotEmpty()
                && it[3].split(',').last()[0] in 'А'..'Я'
        }.map {
            val latitude = it[4].toDouble()
            val longitude = it[5].toDouble()
            it[3].split(',')
                .filter { name -> name[0] in 'А'..'Я' }
                .map { name -> name to Location(latitude, longitude) }
        }
        .flatten()
        .toMap()
        .let {
            logger.info("Found ${it.size} places.")
            repository["0"] = Locations(it)
        }
}
