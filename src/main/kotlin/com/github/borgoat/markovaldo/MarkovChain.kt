package com.github.borgoat.markovaldo

// TODO Multiplatform - see https://github.com/Kotlin/kotlinx.atomicfu
import java.util.concurrent.atomic.AtomicLong

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

  class Builder(val scanner: Scanner = Scanner(), val order: Int = 3) {
    val probs = mutableMapOf<List<Token>, MutableMap<Token, AtomicLong>>()

    fun add(text: CharSequence) {
      val tokens = scanner.scanTokens(text)
      val tokenState = ArrayDeque<Token>()
      tokens.forEach {
        if (tokenState.isNotEmpty()) {
          val innerMap = probs.getOrPut(tokenState.takeLast(order)) { mutableMapOf() }
          innerMap.getOrPut(it) { AtomicLong(0) }.incrementAndGet()
        }
        tokenState.add(it)
      }
    }

    fun build(): MarkovChain {
      val edges = probs.mapValues { generateProbabilityDistribution(it.value) }
      return MarkovChain(order, edges)
    }

    private fun generateProbabilityDistribution(map: Map<Token, AtomicLong>): ProbabilityDistribution<Token> {
      val list = map.map { WeightedItem(it.key, it.value.toLong().toULong()) }
      return ProbabilityDistribution(items = list)
    }
  }
}
