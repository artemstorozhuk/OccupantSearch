package com.occupantsearch.text

import org.koin.core.annotation.Single

@Single
class TextBlockSplitter {
    private val blockSymbols = listOf(
        "?", "!", "\n", "\r",  "\t", ";", ",", ":", "- ", " -", "(", ")", "\"", "<", ">", "[", "]", "“", "”", "«", "»", " "
    )

    fun splitByBlocks(text: String) = blockSymbols
        .fold(text) { acc, e ->
            acc.replace(e, ".")
        }
        .split(".")
        .filter { it.isNotBlank() }
}