package com.github.borgoat.markovaldo

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


fun <E> dequeLimiter(limit: Int) =
    object : ReadOnlyProperty<Any?, ArrayDeque<E>> {

        private val deque: ArrayDeque<E> = ArrayDeque(limit)

        private fun applyLimit() {
            while (deque.size > limit) {
                val removed = deque.removeFirst()
                println("dequeLimiter removed $removed")
            }
        }

        override fun getValue(thisRef: Any?, property: KProperty<*>): ArrayDeque<E> {
            applyLimit()
            return deque
        }
    }


class TokenSequence(
    limit: Int = 3
) {
    private val deque: ArrayDeque<String> by dequeLimiter(limit)

    fun add(string: String) {
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
