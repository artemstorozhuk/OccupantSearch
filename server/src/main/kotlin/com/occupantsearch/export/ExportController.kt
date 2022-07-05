package com.occupantsearch.export

import com.occupantsearch.json.toJson
import com.occupantsearch.occupant.Occupant
import com.occupantsearch.occupant.OccupantController
import com.occupantsearch.vk.postUrl
import org.koin.core.component.KoinComponent

class ExportController(
    private val occupantController: OccupantController
) : KoinComponent {

    fun export(format: Format): String = when (format) {
        Format.JSON -> occupantController.getAll()
            .map { it.asJson() }
            .toJson()
        Format.CSV -> occupantController.getAll()
            .joinToString("\n") { it.asCsv() }
    }

    fun Occupant.asCsv() = """
            ${person.firstname}, ${person.lastname}, ${postIds.joinToString(", ") { it.postUrl }}
        """.trimIndent()

    fun Occupant.asJson() = mapOf(
        "firstname" to person.firstname,
        "lastname" to person.lastname,
        "posts" to postIds.map { it.postUrl },
        "images" to faceImageUrls
    )
}