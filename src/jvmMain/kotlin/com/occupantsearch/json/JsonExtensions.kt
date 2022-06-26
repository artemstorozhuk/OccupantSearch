package com.occupantsearch.json

import com.google.gson.Gson

private val gson = Gson()

fun Any.toJson(): String = gson.toJson(this)

fun <T> String.parseJson(clazz: Class<T>): T = gson.fromJson(this, clazz)
