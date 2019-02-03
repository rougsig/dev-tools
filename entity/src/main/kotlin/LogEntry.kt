package com.github.rougsig.devtools.entity

sealed class LogEntry {
  abstract val name: String
  abstract val time: Long
  abstract val timeDiff: String

  class Init : LogEntry() {
    override val time: Long = System.currentTimeMillis()
    override val timeDiff: String = "+0''000"
    override val name: String = "@@INIT"
  }

  data class PresenterStateChange(
    override val name: String,
    override val time: Long,
    override val timeDiff: String,
    val action: Field,
    val nextState: Field,
    val previousState: Field,
    val stateDiff: Field?,
    val presenterId: String,
    val presenterName: String
  ) : LogEntry()

  data class ScopeChange(
    override val name: String,
    override val time: Long,
    override val timeDiff: String,
    val nextScope: Field,
    val previousScope: Field,
    val scopeDiff: Field?,
    val isOpen: Boolean
  ) : LogEntry()
}