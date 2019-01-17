package com.github.rougsig.devtools.domain

data class Scope(
  val name: String,
  val nextScope: Field,
  val previousScope: Field,
  val scopeDiff: Field?,
  val isOpen: Boolean
) {
  companion object {
    val EMPTY = Scope(
      name = "Scope",
      nextScope = Field.NullField("Scope"),
      previousScope = Field.NullField("Scope"),
      scopeDiff = Field.NullField("Scope"),
      isOpen = true
    )
  }
}