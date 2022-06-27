package com.occupantsearch.properties

import com.occupantsearch.koin.get

fun getProperty(file: String, key: String) = get<PropertiesController>()[file][key]!!