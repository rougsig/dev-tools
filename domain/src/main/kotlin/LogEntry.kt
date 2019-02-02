package com.github.rougsig.devtools.domain

import com.github.rougsig.devtools.domain.mappers.toTime

sealed class LogEntry {
  abstract val name: String
  abstract val time: String

  class Init : LogEntry() {
    override val time: String = System.currentTimeMillis().toTime()
    override val name: String = "@@INIT"
  }

  data class Action(
    override val name: String,
    override val time: String,
    val action: Field,
    val nextState: Field,
    val previousState: Field,
    val stateDiff: Field?
  ) : LogEntry()

  data class Scope(
    override val name: String,
    override val time: String,
    val nextScope: Field,
    val previousScope: Field,
    val scopeDiff: Field?,
    val isOpen: Boolean
  ) : LogEntry()
}
