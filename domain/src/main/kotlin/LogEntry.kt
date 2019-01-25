package com.github.rougsig.devtools.domain

sealed class LogEntry {
  abstract val name: String

  object Init: LogEntry() {
    override val name: String = "@@INIT"
  }

  data class Action(
    override val name: String,
    val action: Field,
    val nextState: Field,
    val previousState: Field,
    val stateDiff: Field?
  ): LogEntry()

  data class Scope(
    override val name: String,
    val nextScope: Field,
    val previousScope: Field,
    val scopeDiff: Field?,
    val isOpen: Boolean
  ): LogEntry()
}