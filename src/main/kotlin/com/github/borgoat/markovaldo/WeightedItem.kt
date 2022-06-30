package com.github.borgoat.markovaldo

import kotlinx.serialization.Serializable

@Serializable
data class WeightedItem<T>(val item: T, val weigth: ULong) : Comparable<WeightedItem<T>> {
    override fun compareTo(other: WeightedItem<T>): Int = compareValuesBy(this, other) { it.weigth }
}
