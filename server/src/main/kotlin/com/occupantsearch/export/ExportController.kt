package com.occupantsearch.export

import com.occupantsearch.json.toJson
import com.occupantsearch.occupant.OccupantController
import org.koin.core.annotation.Single

@Single
class ExportController(
    private val occupantController: OccupantController
) {

    fun export(format: Format): String = when (format) {
        Format.JSON -> occupantController.getAll()
            .map { it.asJson() }
            .toJson()
        Format.CSV -> occupantController.getAll()
            .joinToString("\n") { it.asCsv() }
    }
}