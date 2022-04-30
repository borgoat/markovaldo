package com.github.borgoat.markovaldo

import kotlin.random.Random

class MarkovChain(
    private val edges: Map<TokenSequence, ProbabilityDistribution<String>>,
) {
    tailrec fun generateSentence(tokenSequence: TokenSequence, accumulator: StringBuilder = StringBuilder()): String {
        val t = nextToken(tokenSequence)
        return if (t == null) {
            return accumulator.toString()
        } else {
            accumulator.append(" ").append(t)
            tokenSequence.add(t)
            generateSentence(tokenSequence, accumulator)
        }
    }

    fun nextToken(tokenSequence: TokenSequence): String? =
        edges[tokenSequence]?.next()
}
