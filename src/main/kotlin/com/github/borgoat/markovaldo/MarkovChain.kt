package com.github.borgoat.markovaldo

class MarkovChain(
    private val order: Int = 3,
    private val edges: Map<List<Token>, ProbabilityDistribution<String>>,
) {
    tailrec fun generateSentence(tokenBuffer: List<Token>): String {
        val t = nextToken(tokenBuffer)
        return if (t == null) {
            formatTokens(tokenBuffer)
        } else {
            val nextTokenSequence = tokenBuffer + StringToken(t)
            generateSentence(nextTokenSequence)
        }
    }

    fun nextToken(tokenSequence: List<Token>): String? = edges[tokenSequence.takeLast(order)]?.next()
}
