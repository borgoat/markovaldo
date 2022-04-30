package com.github.borgoat.markovaldo

import kotlin.random.Random
import kotlin.random.nextULong

class ProbabilityDistribution<T>(
    private val random: Random,
    items: List<WeightedItem<T>>,
) {
    // We put items with the  highest weight first, to optimise the linear search for most likely outcomes
    private val sortedItems = items.sortedDescending()
    private val total = items.map { it.weigth }.reduce { acc, weight -> acc + weight }

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
