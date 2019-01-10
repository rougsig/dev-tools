package com.github.rougsig.devtools.domain

data class Scope(
  val list: List<Field>,
  val nextScope: List<Field>,
  val previousScope: List<Field>,
  val diff: List<Field>,
  val diffList: List<Field>
) {
  companion object {
    val EMPTY = Scope(
      list = emptyList(),
      nextScope = emptyList(),
      previousScope = emptyList(),
      diff = emptyList(),
      diffList = emptyList()
    )
  }
}