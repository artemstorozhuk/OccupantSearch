package com.occupantsearch.time

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.Date
import kotlin.time.Duration.Companion.milliseconds

const val secondsInDay = 60 * 60 * 24

fun String.parseDate(): Date = Date.from(LocalDate.parse(this).atStartOfDay().toInstant(ZoneOffset.UTC))

fun Long.millisToSeconds() = (this / 1000).toInt()

fun Int.secondsToMillis() = toLong() * 1000

fun <T> measureDuration(lambda: () -> T): MeasureResult<T> {
    val start = System.currentTimeMillis()
    val result = lambda()
    return MeasureResult(
        result = result,
        duration = (System.currentTimeMillis() - start).milliseconds
    )
}

fun Date.format(): String = SimpleDateFormat("dd-MM-yyyy").format(this)

val Int.secondsStartOfDay get() = this / secondsInDay * secondsInDay
