package com.occupantsearch.vk

import kotlinx.serialization.Serializable

@Serializable
data class PostDto(
    val postId: String,
    val text: String,
    val imageUrls: List<String>,
    val views: Int?,
    val date: Int?,
)