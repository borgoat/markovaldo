package com.github.borgoat.markovaldo

import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

internal class MarkovChainTest {

    @Test
    fun nextToken() {
        val token_il = TokenSequence().apply {
            add("il")
        }
        val token_il_cappone = TokenSequence().apply {
            add("il")
            add("cappone")
        }
        val token_il_cappone_e = TokenSequence().apply {
            add("il")
            add("cappone")
            add("è")
        }
        val token_il_cappone_e_un = TokenSequence().apply {
            add("cappone")
            add("è")
            add("un")
        }
        val token_il_cappone_e_un_gallo = TokenSequence().apply {
            add("è")
            add("un")
            add("gallo")
        }
        val markovChain = MarkovChain(mapOf(
            token_il to ProbabilityDistribution(Random, listOf(WeightedItem("cappone", 1u))),
            token_il_cappone to ProbabilityDistribution(Random, listOf(WeightedItem("è", 1u))),
            token_il_cappone_e to ProbabilityDistribution(Random, listOf(WeightedItem("un", 1u))),
            token_il_cappone_e_un to ProbabilityDistribution(Random, listOf(WeightedItem("gallo", 1u))),
            token_il_cappone_e_un_gallo to ProbabilityDistribution(Random, listOf(WeightedItem("grasso", 1u))),
        ))

        assertEquals("cappone", markovChain.nextToken(token_il))
        assertEquals("è", markovChain.nextToken(token_il_cappone))
        assertEquals("un", markovChain.nextToken(token_il_cappone_e))
        assertEquals("gallo", markovChain.nextToken(token_il_cappone_e_un))
        assertEquals("grasso", markovChain.nextToken(token_il_cappone_e_un_gallo))
    }

    @Test
    fun generateSentence() {
        val token_il = TokenSequence().apply {
            add("il")
        }
        val token_il_cappone = TokenSequence().apply {
            add("il")
            add("cappone")
        }
        val token_il_cappone_e = TokenSequence().apply {
            add("il")
            add("cappone")
            add("è")
        }
        val token_il_cappone_e_un = TokenSequence().apply {
            add("cappone")
            add("è")
            add("un")
        }
        val token_il_cappone_e_un_gallo = TokenSequence().apply {
            add("è")
            add("un")
            add("gallo")
        }
        val markovChain = MarkovChain(mapOf(
            token_il to ProbabilityDistribution(Random, listOf(WeightedItem("cappone", 1u))),
            token_il_cappone to ProbabilityDistribution(Random, listOf(WeightedItem("è", 1u))),
            token_il_cappone_e to ProbabilityDistribution(Random, listOf(WeightedItem("un", 1u))),
            token_il_cappone_e_un to ProbabilityDistribution(Random, listOf(WeightedItem("gallo", 1u))),
            token_il_cappone_e_un_gallo to ProbabilityDistribution(Random, listOf(WeightedItem("grasso", 1u))),
        ))

        val x = markovChain.generateSentence(token_il)
        assertEquals("il cappone è un gallo grasso", x)
    }
}
