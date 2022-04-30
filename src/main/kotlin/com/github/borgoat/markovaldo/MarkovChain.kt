package com.github.borgoat.markovaldo

import kotlin.random.Random

class MarkovChain(
    private val edges: Map<TokenSequence, ProbabilityDistribution<String>>,
) {
    fun generateSentence(tokenSequence: TokenSequence): String {
        val t = nextToken(tokenSequence)
        return if (t == null) {
            tokenSequence.toString()
        } else {
            tokenSequence.add(t)
            generateSentence(tokenSequence)
        }
    }

    fun nextToken(tokenSequence: TokenSequence): String? =
        edges[tokenSequence]?.next()
}
