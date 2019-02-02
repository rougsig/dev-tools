package com.github.rougsig.devtools.domain

sealed class LogEntry {
  abstract val name: String
  abstract val time: Long

  class Init : LogEntry() {
    override val time: Long = System.currentTimeMillis()
    override val name: String = "@@INIT"
  }

  data class Action(
    override val name: String,
    override val time: Long,
    val action: Field,
    val nextState: Field,
    val previousState: Field,
    val stateDiff: Field?
  ) : LogEntry()

  data class Scope(
    override val name: String,
    override val time: Long,
    val nextScope: Field,
    val previousScope: Field,
    val scopeDiff: Field?,
    val isOpen: Boolean
  ) : LogEntry()
}
