package com.github.rougsig.devtools.domain

data class Scope(
  val scope: List<Field>
) {
  companion object {
    val EMPTY = Scope(
      scope = emptyList()
    )
  }
}