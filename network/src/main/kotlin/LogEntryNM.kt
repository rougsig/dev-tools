package com.github.rougsig.devtools.network

import com.google.gson.JsonObject

sealed class LogEntryNM {
  data class Action(
    val name: String,
    val action: JsonObject,
    val nextState: JsonObject,
    val previousState: JsonObject
  ) : LogEntryNM() {
    companion object {
      val INIT = Action(
        name = "Init",
        action = JsonObject(),
        nextState = JsonObject(),
        previousState = JsonObject()
      )
    }
  }

  abstract class Scope : LogEntryNM() {
    abstract val name: String
    abstract val scopeJsonObject: JsonObject
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
  ) : Scope() {
    override val scopeJsonObject: JsonObject
      get() = scope
  }

  data class ScopeClose(
    override val name: String,
    val rootScope: JsonObject
  ) : Scope() {
    override val scopeJsonObject: JsonObject
      get() = rootScope
  }
}
