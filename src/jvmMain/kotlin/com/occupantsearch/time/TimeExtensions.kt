package com.occupantsearch.time

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.Date
import kotlin.system.measureTimeMillis
import kotlin.time.Duration.Companion.milliseconds

fun String.parseDate(): Date = Date.from(LocalDate.parse(this).atStartOfDay().toInstant(ZoneOffset.UTC))

fun Long.millisToSeconds(): Int = (this / 1000).toInt()

fun Int.secondsToMillis(): Long = toLong() * 1000

fun measureDuration(lambda: () -> Unit) = measureTimeMillis(lambda).milliseconds

fun Int.secondsToDay() = this / 60 / 60 / 24 * 60 * 60 * 24

fun Int.secondsToDayFormat(): String = SimpleDateFormat("dd-MM-yyyy").format(secondsToMillis())
