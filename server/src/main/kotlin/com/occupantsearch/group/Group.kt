package com.occupantsearch.group

data class Group(
    val ownerId: Int,
    val name: String? = null,
    val url: String? = null,
    val avatar: String? = null,
)

fun Group.hasInformation() = name != null && url != null && avatar != null