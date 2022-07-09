package com.occupantsearch.person

data class Person(
    val firstname: String,
    val lastname: String,
) {
    fun matches(query: String) =
        query.split(" ").all {
            firstname.startsWith(prefix = it, ignoreCase = true) ||
                lastname.startsWith(prefix = it, ignoreCase = true)
        }

    val fullName get() = "$firstname $lastname"
}