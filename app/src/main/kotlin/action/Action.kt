package com.github.rougsig.devtools.app.action

import java.util.*

data class Action(
  val id: String = UUID.randomUUID().toString()
) {
  companion object {
    val EMPTY = Action("")
  }
}