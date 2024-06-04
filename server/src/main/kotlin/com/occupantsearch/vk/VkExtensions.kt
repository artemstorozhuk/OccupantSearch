package com.occupantsearch.vk

val String.postUrl get() = "https://vk.com/wall$this"

val String.mobilePostUrl get() = "https://m.vk.com/wall$this"