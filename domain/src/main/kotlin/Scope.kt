package com.github.rougsig.devtools.domain

data class Scope(
  val name: String,
  val list: List<Field>,
  val nextScope: List<Field>,
  val previousScope: List<Field>,
  val diff: List<Field>,
  val diffList: List<Field>,
  val isOpen: Boolean
) {
  companion object {
    val EMPTY = Scope(
      name = "",
      list = emptyList(),
      nextScope = emptyList(),
      previousScope = emptyList(),
      diff = emptyList(),
      diffList = emptyList(),
      isOpen = true
    )
  }
}