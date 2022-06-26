package com.occupantsearch.lang

import java.util.stream.Collectors
import java.util.stream.Stream

fun <T> retryUntilSuccess(
    lambda: () -> T,
    onError: (Throwable) -> Boolean = { true }
): T? {
    while (true) {
        try {
            return lambda()
        } catch (e: Throwable) {
            if (!onError(e)) {
                return null
            }
        }
    }
}

fun <K, V> Stream<Pair<K, V>>.toMap(): Map<K, V> = collect(Collectors.toMap({ it.first }, { it.second }))