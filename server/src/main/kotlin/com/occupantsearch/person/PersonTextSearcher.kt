package com.occupantsearch.person

import com.occupantsearch.resource.ResourceReader
import com.occupantsearch.text.TextBlockSplitter
import org.koin.core.annotation.Single

@Single
class PersonTextSearcher(
    resourceReader: ResourceReader,
    private val textBlockSplitter: TextBlockSplitter
) {
    private val names = resourceReader.readResourceAsSet("/names/firstnames.txt")
    private val surnames = resourceReader.readResourceAsSet("/names/lastnames.txt")

    fun search(text: String) =
        textBlockSplitter.splitByBlocks(text)
            .asSequence()
            .map { block ->
                val words = block.split(" ")
                    .filter { it.isNotBlank() }
                words.foldIndexed(PersonSearch(setOf(), 0)) { index, acc, word ->
                    when {
                        index == words.size - 1 || index < acc.index -> acc
                        word in names && words[index + 1] in surnames ->
                            PersonSearch(acc.persons + Person(word, words[index + 1]), index + 2)
                        word in surnames && words[index + 1] in names ->
                            PersonSearch(acc.persons + Person(words[index + 1], word), index + 2)
                        else -> acc
                    }
                }.persons
            }
            .flatten()
            .toSet()

    data class PersonSearch(
        val persons: Set<Person>,
        val index: Int
    )
}