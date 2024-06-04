package com.occupantsearch.time

import kotlin.time.Duration

data class MeasureResult<T>(
    val result: T,
    val duration: Duration
)