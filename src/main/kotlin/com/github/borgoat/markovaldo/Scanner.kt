package com.github.borgoat.markovaldo

const val INIT_ACC = ""
private val PUNCTUATION = setOf('.', ',', '!', ':', ';', '"', '\'', '.', '-')

class Scanner {

  fun scanTokens(charSequence: CharSequence): Sequence<Token> = sequence {
    yield(MarkerToken(MarkerType.BEGIN))
    val lastToken =
        charSequence.fold(INIT_ACC) { acc: String, c: Char ->
          if (c.isWhitespace()) {
            if (acc.isNotBlank()) yield(StringToken(acc))
            return@fold INIT_ACC
          }

          if (c in PUNCTUATION) {
            if (acc.isNotBlank()) yield(StringToken(acc))
            yield(StringToken(c.toString()))
            return@fold INIT_ACC
          }

          return@fold acc.plus(c)
        }

    if (lastToken.isNotBlank()) yield(StringToken(lastToken))

    yield(MarkerToken(MarkerType.END))
  }
}
