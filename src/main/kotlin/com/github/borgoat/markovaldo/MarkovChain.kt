package com.github.borgoat.markovaldo

import kotlinx.serialization.Serializable

@Serializable
class MarkovChain(
    private val order: Int = 3,
    private val edges: Map<List<Token>, ProbabilityDistribution<Token>>,
) {
  tailrec fun generateSentence(tokenBuffer: List<Token>): String {
    val t = nextToken(tokenBuffer)
    return if (t == null) {
      formatTokens(tokenBuffer)
    } else {
      val nextTokenSequence = tokenBuffer + t
      generateSentence(nextTokenSequence)
    }
  }

  fun nextToken(tokenSequence: List<Token>): Token? = edges[tokenSequence.takeLast(order)]?.next()
}
