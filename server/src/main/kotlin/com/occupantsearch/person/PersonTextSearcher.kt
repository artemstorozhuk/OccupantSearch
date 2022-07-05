package com.occupantsearch.person

import com.occupantsearch.resource.ResourceReader
import com.occupantsearch.text.TextBlockSplitter
import org.koin.core.component.KoinComponent

class PersonTextSearcher(
    resourceReader: ResourceReader,
    private val textBlockSplitter: TextBlockSplitter
) : KoinComponent {
    private val names = resourceReader.readResourceAsSet("/names/firstnames.txt")
    private val surnames = resourceReader.readResourceAsSet("/names/lastnames.txt")

    fun search(text: String) =
        textBlockSplitter.splitByBlocks(text)
            .map { block ->
                val words = block.split(" ")
                    .filter { it.isNotBlank() }
                words.mapIndexedNotNull { index, word ->
                    if (word in names) {
                        if (index < words.size - 1 && words[index + 1] in surnames) {
                            Person(word, words[index + 1])
                        } else if (index > 0 && words[index - 1] in surnames) {
                            Person(word, words[index - 1])
                        } else {
                            null
                        }
                    } else {
                        null
                    }
                }
            }
            .flatten()
            .toSet()
}