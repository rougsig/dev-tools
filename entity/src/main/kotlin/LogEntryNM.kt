package com.github.rougsig.devtools.entity

sealed class LogEntryNM {
  abstract val time: Long

  data class PresenterStateChange(
    val name: String,
    override val time: Long,
    val action: Field,
    val nextState: Field,
    val previousState: Field,
    val presenterId: String,
    val presenterName: String
  ) : LogEntryNM() {
    companion object {
      fun INIT() = PresenterStateChange(
        name = "@@INIT",
        time = System.currentTimeMillis(),
        action = Field.NullField("PresenterStateChange"),
        nextState = Field.NullField("State"),
        previousState = Field.NullField("State"),
        presenterId = "InitPresenterId",
        presenterName = "InitPresenterName"
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

