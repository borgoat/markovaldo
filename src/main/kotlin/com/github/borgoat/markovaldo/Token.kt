package com.github.borgoat.markovaldo

import kotlinx.serialization.Serializable

@Serializable
sealed class Token

@Serializable
data class StringToken(val string: String) : Token() {
    val skipSpaceAfter = noSpaceAfter.matches(string)
    val skipSpaceBefore = noSpaceBefore.matches(string)

    companion object {
        private val noSpaceAfter = "^([\"\']+|\\-\\-)\$".toRegex()
        private val noSpaceBefore = "^[\"\'\\.!?,:\\-]+\$".toRegex()
    }
}

enum class MarkerType {
    BEGIN, END,
}

@Serializable
data class MarkerToken(val markerType: MarkerType) : Token()

fun formatTokens(tokens: List<Token>): String {
    val stringBuilder = StringBuilder()
    var previousSkipsSpaceAfter = true
    tokens.forEach {
        if (it is MarkerToken) return@forEach
        it as StringToken

        if (!previousSkipsSpaceAfter && !it.skipSpaceBefore) stringBuilder.append(' ')
        previousSkipsSpaceAfter = it.skipSpaceAfter

        stringBuilder.append(it.string)
    }
    return stringBuilder.toString()
}
