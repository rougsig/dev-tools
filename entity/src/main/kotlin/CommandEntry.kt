package com.github.rougsig.devtools.entity

sealed class CommandEntry {
  data class SetPresenterState(
    val id: String,
    val name: String,
    val state: Field
  ) : CommandEntry()
}
