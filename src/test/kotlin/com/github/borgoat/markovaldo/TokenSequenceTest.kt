package com.github.borgoat.markovaldo

import kotlin.test.Test
import kotlin.test.assertEquals

internal class TokenSequenceTest {

    @Test
    fun add() {
        val a = TokenSequence()
        val b = TokenSequence()

        a.apply {
            add("a")
            add("b")
            add("c")
            add("d")
        }
        b.apply {
            add("b")
            add("c")
            add("d")
        }

        assertEquals(a, b)
        assertEquals(a.hashCode(), b.hashCode())
    }
}
