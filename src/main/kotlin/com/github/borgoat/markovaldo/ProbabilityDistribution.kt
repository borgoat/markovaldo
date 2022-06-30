package com.github.borgoat.markovaldo

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlin.random.Random
import kotlin.random.nextULong

@Serializable
class ProbabilityDistribution<T> private constructor(
    @Transient private val random: Random = Random,
    private val sortedItems: List<WeightedItem<T>>,
    private val total: ULong,
) {
    constructor(
        random: Random = Random,
        items: List<WeightedItem<T>>
    ) : this(
        random = random,
        sortedItems = items.sortedDescending(),
        total = items.map { it.weigth }.reduce { acc, weight -> acc + weight },
    )

    operator fun next(): T {
        val x = random.nextULong(total)
        var i = 0uL
        val w = sortedItems.first {
            i += it.weigth
            x < i
        }
        return w.item
    }
}
