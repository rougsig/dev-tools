package com.github.rougsig.devtools.domain.mappers

fun Long.toTime(): String {
  val millis = "${this % 1000}"
  val seconds = "${this / 1000}"

  return "+$seconds''$millis"
}
