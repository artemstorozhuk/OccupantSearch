package com.occupantsearch.analytics

import com.occupantsearch.occupant.OccupantController
import com.occupantsearch.time.secondsStartOfDay
import org.koin.core.component.KoinComponent
import java.util.concurrent.atomic.AtomicReference
import java.util.stream.Collectors

class OccupantsCountByDateController(
    private val occupantController: OccupantController,
) : KoinComponent {
    private val occupantsCountByDateReference = AtomicReference<Map<Int, Long>>(emptyMap())

    fun refresh() = occupantsCountByDateReference.set(
        occupantController.getAll().stream()
            .parallel()
            .map { it.date.secondsStartOfDay }
            .collect(Collectors.groupingByConcurrent({ it }, Collectors.counting()))
            .toSortedMap()
    )

    fun getOccupantsCountByDate(): Map<Int, Long> = occupantsCountByDateReference.get()
}