package com.github.borgoat.markovaldo

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

internal class MarkovChainTest {

    @Test
    fun serialization() {
        val allTokens =
            listOf(
                MarkerToken(MarkerType.BEGIN),
                StringToken("il"),
                StringToken("cappone"),
                StringToken("è"),
                StringToken("un"),
                StringToken("gallo"),
                StringToken("grasso"),
            )
        val markovChain =
            MarkovChain(
                order = 3,
                edges =
                mapOf(
                    allTokens.slice(0..0) to
                            ProbabilityDistribution(
                                Random, listOf(WeightedItem(StringToken("il"), 1u))
                            ),
                    allTokens.slice(0..1) to
                            ProbabilityDistribution(
                                Random, listOf(WeightedItem(StringToken("cappone"), 1u))
                            ),
                    allTokens.slice(0..2) to
                            ProbabilityDistribution(
                                Random, listOf(WeightedItem(StringToken("è"), 1u))
                            ),
                    allTokens.slice(1..3) to
                            ProbabilityDistribution(
                                Random, listOf(WeightedItem(StringToken("un"), 1u))
                            ),
                    allTokens.slice(2..4) to
                            ProbabilityDistribution(
                                Random, listOf(WeightedItem(StringToken("gallo"), 1u))
                            ),
                    allTokens.slice(3..5) to
                            ProbabilityDistribution(
                                Random, listOf(WeightedItem(StringToken("grasso"), 1u))
                            ),
                )
            )

        val format = Json { allowStructuredMapKeys = true }
        val encoded = format.encodeToJsonElement(markovChain)

        val decoded = format.decodeFromJsonElement<MarkovChain>(encoded)
        assertEquals(StringToken("il"), decoded.nextToken(allTokens.take(1)))
        assertEquals(StringToken("cappone"), decoded.nextToken(allTokens.take(2)))
        assertEquals(StringToken("è"), decoded.nextToken(allTokens.take(3)))
        assertEquals(StringToken("un"), decoded.nextToken(allTokens.take(4)))
        assertEquals(StringToken("gallo"), decoded.nextToken(allTokens.take(5)))
        assertEquals(StringToken("grasso"), decoded.nextToken(allTokens.take(6)))
    }

    @Test
    fun nextToken() {
        val allTokens =
            listOf(
                MarkerToken(MarkerType.BEGIN),
                StringToken("il"),
                StringToken("cappone"),
                StringToken("è"),
                StringToken("un"),
                StringToken("gallo"),
                StringToken("grasso"),
            )
        val markovChain =
            MarkovChain(
                order = 3,
                edges =
                mapOf(
                    allTokens.slice(0..0) to
                            ProbabilityDistribution(
                                Random, listOf(WeightedItem(StringToken("il"), 1u))
                            ),
                    allTokens.slice(0..1) to
                            ProbabilityDistribution(
                                Random, listOf(WeightedItem(StringToken("cappone"), 1u))
                            ),
                    allTokens.slice(0..2) to
                            ProbabilityDistribution(
                                Random, listOf(WeightedItem(StringToken("è"), 1u))
                            ),
                    allTokens.slice(1..3) to
                            ProbabilityDistribution(
                                Random, listOf(WeightedItem(StringToken("un"), 1u))
                            ),
                    allTokens.slice(2..4) to
                            ProbabilityDistribution(
                                Random, listOf(WeightedItem(StringToken("gallo"), 1u))
                            ),
                    allTokens.slice(3..5) to
                            ProbabilityDistribution(
                                Random, listOf(WeightedItem(StringToken("grasso"), 1u))
                            ),
                )
            )

        val format = Json { allowStructuredMapKeys = true }
        val encoded = format.encodeToJsonElement(markovChain)

        assertEquals(StringToken("il"), markovChain.nextToken(allTokens.take(1)))
        assertEquals(StringToken("cappone"), markovChain.nextToken(allTokens.take(2)))
        assertEquals(StringToken("è"), markovChain.nextToken(allTokens.take(3)))
        assertEquals(StringToken("un"), markovChain.nextToken(allTokens.take(4)))
        assertEquals(StringToken("gallo"), markovChain.nextToken(allTokens.take(5)))
        assertEquals(StringToken("grasso"), markovChain.nextToken(allTokens.take(6)))
    }

    @Test
    fun generateSentence() {
        val allTokens =
            listOf(
                MarkerToken(MarkerType.BEGIN),
                StringToken("il"),
                StringToken("cappone"),
                StringToken("è"),
                StringToken("un"),
                StringToken("gallo"),
                StringToken("grasso"),
                StringToken("!"),
            )
        val markovChain =
            MarkovChain(
                order = 3,
                edges =
                mapOf(
                    allTokens.slice(0..0) to
                            ProbabilityDistribution(
                                Random, listOf(WeightedItem(StringToken("il"), 1u))
                            ),
                    allTokens.slice(0..1) to
                            ProbabilityDistribution(
                                Random, listOf(WeightedItem(StringToken("cappone"), 1u))
                            ),
                    allTokens.slice(0..2) to
                            ProbabilityDistribution(
                                Random, listOf(WeightedItem(StringToken("è"), 1u))
                            ),
                    allTokens.slice(1..3) to
                            ProbabilityDistribution(
                                Random, listOf(WeightedItem(StringToken("un"), 1u))
                            ),
                    allTokens.slice(2..4) to
                            ProbabilityDistribution(
                                Random, listOf(WeightedItem(StringToken("gallo"), 1u))
                            ),
                    allTokens.slice(3..5) to
                            ProbabilityDistribution(
                                Random, listOf(WeightedItem(StringToken("grasso"), 1u))
                            ),
                    allTokens.slice(4..6) to
                            ProbabilityDistribution(
                                Random, listOf(WeightedItem(StringToken("!"), 1u))
                            ),
                )
            )

        val x =
            markovChain.generateSentence(listOf(MarkerToken(MarkerType.BEGIN), StringToken("il")))
        assertEquals("il cappone è un gallo grasso!", x)
    }
}
