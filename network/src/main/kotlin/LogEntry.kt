package com.github.rougsig.devtools.network

import com.google.gson.JsonObject

sealed class LogEntry(internal val type: Type) {
  internal enum class Type {
    Action,
    Scope
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

  data class Scope(
    val name: String,
    val scope: JsonObject
  ) : LogEntry(Type.Scope) {
    companion object {
      val INIT = Scope(
        name = "Init",
        scope = JsonObject()
      )
    }
  }
}
