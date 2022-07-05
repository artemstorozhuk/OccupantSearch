package com.occupantsearch.person

import kotlinx.serialization.Serializable

@Serializable
data class Person(
    val firstname: String,
    val lastname: String,
) {
    fun matches(query: String) =
        query.split(" ").all {
            firstname.startsWith(prefix = it, ignoreCase = true) ||
                lastname.startsWith(prefix = it, ignoreCase = true)
        }

    fun fullName() = "$firstname $lastname"
}