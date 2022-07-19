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

    fun search(text: String): Set<Person> {
        val persons = HashSet<Person>()
        textBlockSplitter.splitByBlocks(text).forEach { block ->
            val words = block.split(" ")
                .filter { it.isNotBlank() }
            var index = 0
            while (index < words.size - 1) {
                val word = words[index]
                when {
                    word in names && words[index + 1] in surnames -> {
                        persons.add(Person(word, words[index + 1]))
                        index += 2
                    }
                    word in surnames  && words[index + 1] in names -> {
                        persons.add(Person(words[index + 1], word))
                        index += 2
                    }
                    else -> index++
                }
            }
        }
        return persons
    }
}