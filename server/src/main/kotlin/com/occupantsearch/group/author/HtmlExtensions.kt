package com.occupantsearch.group.author

fun getAvatarUrl(style: String): String {
    val from = style.indexOf("url('")
    val to = style.indexOf("')", from)
    return style.substring(from + "url('".length, to)
}