package com.github.borgoat.markovaldo

import kotlin.test.Test
import kotlin.test.assertContentEquals

internal class ScannerTest {

    private val scanner = Scanner()

    @Test
    fun scanTokens_one() {
        val tokens = scanner.scanTokens("Il cappone è un gallo  grasso!")
        assertContentEquals(
            listOf(
                MarkerToken(MarkerType.BEGIN),
                StringToken("Il"),
                StringToken("cappone"),
                StringToken("è"),
                StringToken("un"),
                StringToken("gallo"),
                StringToken("grasso"),
                StringToken("!"),
                MarkerToken(MarkerType.END),
            ),
            tokens.toList(),
        )
    }

    @Test
    fun scanTokens_two() {
        val tokens = scanner.scanTokens("""
            "Quando mi incazzo divento brutto!" - ... "Ma, veramente, è già sulla buona strada..."
        """.trimIndent())
        assertContentEquals(
            listOf(
                MarkerToken(MarkerType.BEGIN),
                StringToken("\""),
                StringToken("Quando"),
                StringToken("mi"),
                StringToken("incazzo"),
                StringToken("divento"),
                StringToken("brutto"),
                StringToken("!"),
                StringToken("\""),
                StringToken("-"),
                StringToken("."),
                StringToken("."),
                StringToken("."),
                StringToken("\""),
                StringToken("Ma"),
                StringToken(","),
                StringToken("veramente"),
                StringToken(","),
                StringToken("è"),
                StringToken("già"),
                StringToken("sulla"),
                StringToken("buona"),
                StringToken("strada"),
                StringToken("."),
                StringToken("."),
                StringToken("."),
                StringToken("\""),
                MarkerToken(MarkerType.END),
            ),
            tokens.toList(),
        )
    }
}
