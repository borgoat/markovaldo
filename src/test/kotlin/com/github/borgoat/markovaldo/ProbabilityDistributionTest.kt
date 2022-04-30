package com.github.borgoat.markovaldo

import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertTrue

internal class ProbabilityDistributionTest {

    @Test
    operator fun next() {
        val a = WeightedItem("a", 1u)
        val b = WeightedItem("b", 9u)
        val probabilityDistribution = ProbabilityDistribution(
            Random,
            listOf(a, b),
        )

        val m = mutableMapOf(Pair("a", 0), Pair("b", 0))

        for (i in 1..100) {
            val x = probabilityDistribution.next()
            m.computeIfPresent(x) { _, v -> v + 1 }
        }

        assertTrue { m["a"]!! > 1 }
        assertTrue { m["b"]!! > 1 }
    }
}