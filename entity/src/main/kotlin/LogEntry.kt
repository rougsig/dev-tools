package com.github.rougsig.devtools.entity

sealed class LogEntry {
  abstract val name: String
  abstract val time: Long
  abstract val timeDiff: String

  abstract fun copy(newTimeDiff: String): LogEntry

  class Init : LogEntry() {
    override val time: Long = System.currentTimeMillis()
    override val timeDiff: String = "+0''000"
    override val name: String = "@@INIT"

    override fun copy(newTimeDiff: String): LogEntry = this
  }

  data class Action(
    override val name: String,
    override val time: Long,
    override val timeDiff: String,
    val action: Field,
    val nextState: Field,
    val previousState: Field,
    val stateDiff: Field?
  ) : LogEntry() {
    override fun copy(newTimeDiff: String): LogEntry {
      return this.copy(timeDiff = newTimeDiff)
    }
  }

  data class ScopeChange(
    override val name: String,
    override val time: Long,
    override val timeDiff: String,
    val nextScope: Field,
    val previousScope: Field,
    val scopeDiff: Field?,
    val isOpen: Boolean
  ) : LogEntry() {
    override fun copy(newTimeDiff: String): LogEntry {
      return this.copy(timeDiff = newTimeDiff)
    }
  }

  data class Message(
    override val name: String,
    override val time: Long,
    override val timeDiff: String,
    val message: Field
  ) : LogEntry() {
    override fun copy(newTimeDiff: String): LogEntry {
      return this.copy(timeDiff = newTimeDiff)
    }
  }
}
