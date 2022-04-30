package com.github.borgoat.markovaldo

import kotlin.test.Test
import kotlin.test.assertTrue

internal class WeightedItemTest {

    @Test
    fun compareTo() {
        val a = WeightedItem("a", 10u)
        val b = WeightedItem("b", 1u)
        assertTrue { a > b }
    }
}
