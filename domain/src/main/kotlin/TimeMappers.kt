package com.github.rougsig.devtools.domain

internal fun createTimeDiff(previous: Long, next: Long): String {
  val diff = next - previous

  val millis = "${diff % 1000}"
  val seconds = "${diff / 1000}"

  return "+$seconds''$millis"
}
