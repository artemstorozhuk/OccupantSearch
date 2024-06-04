package com.occupantsearch.concurrent

import java.util.concurrent.locks.ReentrantLock


class ValueLock(
    private val size: Int = Runtime.getRuntime().availableProcessors()
) {
    private val locks = (0..size).map { ReentrantLock() }

    private val Any.index: Int
        get() {
            val mod = hashCode() % size
            return if (mod < 0) {
                mod + size
            } else {
                mod
            }
        }

    fun lock(value: Any, lambda: () -> Unit) = locks[value.index].run {
        lock()
        try {
            lambda()
        } finally {
            unlock()
        }
    }
}