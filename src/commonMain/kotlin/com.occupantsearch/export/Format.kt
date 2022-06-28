package com.occupantsearch.export

enum class Format(
    val value: String
) {
    JSON("json"),
    CSV("csv"),
}

fun from(string: String) = Format.values().firstOrNull { it.value == string }