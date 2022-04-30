package com.github.borgoat.markovaldo

import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

internal class MarkovChainTest {

    @Test
    fun nextToken() {
        val allTokens = listOf(
            MarkerToken(MarkerType.BEGIN),
            StringToken("il"),
            StringToken("cappone"),
            StringToken("è"),
            StringToken("un"),
            StringToken("gallo"),
            StringToken("grasso"),
        )
        val markovChain = MarkovChain(
            order = 3,
            edges = mapOf(
                allTokens.slice(0..0) to ProbabilityDistribution(Random, listOf(WeightedItem("il", 1u))),
                allTokens.slice(0..1) to ProbabilityDistribution(Random, listOf(WeightedItem("cappone", 1u))),
                allTokens.slice(0..2) to ProbabilityDistribution(Random, listOf(WeightedItem("è", 1u))),
                allTokens.slice(1..3) to ProbabilityDistribution(Random, listOf(WeightedItem("un", 1u))),
                allTokens.slice(2..4) to ProbabilityDistribution(Random, listOf(WeightedItem("gallo", 1u))),
                allTokens.slice(3..5) to ProbabilityDistribution(Random, listOf(WeightedItem("grasso", 1u))),
            )
        )

        assertEquals("il", markovChain.nextToken(allTokens.take(1)))
        assertEquals("cappone", markovChain.nextToken(allTokens.take(2)))
        assertEquals("è",markovChain.nextToken(allTokens.take(3)))
        assertEquals("un", markovChain.nextToken(allTokens.take(4)))
        assertEquals("gallo", markovChain.nextToken(allTokens.take(5)))
        assertEquals("grasso", markovChain.nextToken(allTokens.take(6)))
    }

    @Test
    fun generateSentence() {
        val allTokens = listOf(
            MarkerToken(MarkerType.BEGIN),
            StringToken("il"),
            StringToken("cappone"),
            StringToken("è"),
            StringToken("un"),
            StringToken("gallo"),
            StringToken("grasso"),
            StringToken("!"),
        )
        val markovChain = MarkovChain(
            order = 3,
            edges = mapOf(
                allTokens.slice(0..0) to ProbabilityDistribution(Random, listOf(WeightedItem("il", 1u))),
                allTokens.slice(0..1) to ProbabilityDistribution(Random, listOf(WeightedItem("cappone", 1u))),
                allTokens.slice(0..2) to ProbabilityDistribution(Random, listOf(WeightedItem("è", 1u))),
                allTokens.slice(1..3) to ProbabilityDistribution(Random, listOf(WeightedItem("un", 1u))),
                allTokens.slice(2..4) to ProbabilityDistribution(Random, listOf(WeightedItem("gallo", 1u))),
                allTokens.slice(3..5) to ProbabilityDistribution(Random, listOf(WeightedItem("grasso", 1u))),
                allTokens.slice(4..6) to ProbabilityDistribution(Random, listOf(WeightedItem("!", 1u))),
            )
        )

        val x = markovChain.generateSentence(listOf(MarkerToken(MarkerType.BEGIN), StringToken("il")))
        assertEquals("il cappone è un gallo grasso!", x)
    }
}
