package com.github.rougsig.devtools.domain

internal fun getName(name: String): String {
  return name
    .split(".")
    .takeLast(2)
    .joinToString(".") { it }
}