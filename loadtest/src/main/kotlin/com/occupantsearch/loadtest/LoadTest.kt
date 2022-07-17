package com.occupantsearch.loadtest

import java.net.URL
import java.util.Date
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong

private const val url = "http://localhost:8080"
private val now get() = Date().time
private val counterRef = AtomicInteger(0)
private val timeRef = AtomicLong(0)

fun main() {
    val start = now
    timeRef.set(start)
    for (i in 0..Runtime.getRuntime().availableProcessors()) {
        Thread {
            while (true) {
                URL("$url/api/v1/occupants")
                    .openStream()
                    .close()
                val time = now
                val count = counterRef.getAndIncrement()
                if (time - timeRef.get() > 1000) {
                    timeRef.set(time)
                    val requestsCount = count / ((time - start) / 1000)
                    println("Completed $requestsCount per second to $url")
                }
            }
        }.start()
    }
    while (true) {
    }
}
