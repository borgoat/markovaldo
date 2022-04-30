package com.github.borgoat.markovaldo

class TokenSequence(private val limit: Int = 3) {
  private val deque: ArrayDeque<String> = ArrayDeque(limit)

  fun add(string: String) {
    if (deque.size == limit) {
      deque.removeFirst()
    }
    deque.add(string)
  }

  override fun hashCode(): Int {
    return deque.hashCode()
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as TokenSequence

    if (deque != other.deque) return false

    return true
  }
}
