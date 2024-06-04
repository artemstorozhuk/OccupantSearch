package com.occupantsearch.geo

import org.koin.core.annotation.Single
import org.slf4j.LoggerFactory
import java.io.File

// http://download.geonames.org/export/dump/
@Single
class GeoNamesParser(
    private val locationsController: LocationController
) {
    private val logger = LoggerFactory.getLogger(GeoNamesParser::class.java)

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
            locationsController.updateLocations(it)
        }
}
