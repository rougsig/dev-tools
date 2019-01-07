package com.github.rougsig.devtools.domain

import javafx.scene.image.Image
import java.io.ByteArrayInputStream

data class Action(
  val name: String,
  val fields: List<Field>,
  val nextState: List<Field>,
  val previousState: List<Field>,
  val diff: List<Field>,
  @Transient
  val time: String? = null
) {
  companion object {
    private val emptyImage = Image(ByteArrayInputStream(ByteArray(0)))

    val EMPTY = Action(
      "",
      mutableListOf(),
      mutableListOf(),
      mutableListOf(),
      mutableListOf()
    )
  }
}