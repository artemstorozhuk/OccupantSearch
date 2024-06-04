package com.occupantsearch.group

import kotlinx.serialization.Serializable

@Serializable
data class GroupDto(
    val name: String,
    val count: Long,
    val url: String,
    val avatar: String,
)