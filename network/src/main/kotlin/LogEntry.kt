package com.github.rougsig.devtools.network

import com.google.gson.JsonObject

sealed class LogEntry(internal val type: Type) {
  internal enum class Type {
    Action,
    ScopeOpen,
    ScopeClose
  }

  data class Action(
    val name: String,
    val action: JsonObject,
    val nextState: JsonObject,
    val previousState: JsonObject
  ) : LogEntry(Type.Action) {
    companion object {
      val INIT = Action(
        name = "Init",
        action = JsonObject(),
        nextState = JsonObject(),
        previousState = JsonObject()
      )
    }
  }

  interface Scope {
    val name: String
    fun getScopeJson(): JsonObject
    val isOpen
      get() = this is ScopeOpen

    companion object {
      val INIT = ScopeOpen(
        name = "INIT",
        scope = JsonObject()
      )
    }
  }

  data class ScopeOpen(
    override val name: String,
    val scope: JsonObject
  ) : LogEntry(Type.ScopeOpen), Scope {
    override fun getScopeJson() = scope
  }

  data class ScopeClose(
    override val name: String,
    val rootScope: JsonObject
  ) : LogEntry(Type.ScopeClose), Scope {
    override fun getScopeJson() = rootScope
  }
}
