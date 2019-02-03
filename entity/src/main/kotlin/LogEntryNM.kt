package com.github.rougsig.devtools.entity

sealed class LogEntryNM {
  abstract val time: Long

  data class Action(
    val name: String,
    override val time: Long,
    val action: Field,
    val nextState: Field,
    val previousState: Field
  ) : LogEntryNM() {
    companion object {
      fun INIT() = Action(
        name = "@@INIT",
        time = System.currentTimeMillis(),
        action = Field.NullField("Action"),
        nextState = Field.NullField("State"),
        previousState = Field.NullField("State")
      )
    }
  }

  data class Message(
    val name: String,
    override val time: Long,
    val message: Field
  ) : LogEntryNM() {
    companion object {
      fun INIT() = Message(
        name = "@@INIT",
        time = System.currentTimeMillis(),
        message = Field.NullField("Message")
      )
    }
  }

  interface Scope {
    val name: String
    val time: Long
    val scope: Field
  }

  data class ScopeOpen(
    override val name: String,
    override val time: Long,
    override val scope: Field
  ) : LogEntryNM(), Scope {
    companion object {
      fun INIT() = ScopeOpen(
        name = "@@INIT",
        time = System.currentTimeMillis(),
        scope = Field.NullField("Root")
      )
    }
  }

  data class ScopeClose(
    override val name: String,
    override val time: Long,
    val rootScope: Field
  ) : LogEntryNM(), Scope {
    override val scope: Field
      get() = rootScope
  }
}

