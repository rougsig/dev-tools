package com.github.rougsig.devtools.network

import com.google.gson.JsonObject

data class ScopeLog(
  val scope: JsonObject
) {
  companion object {
    val INIT = ScopeLog(
      scope = JsonObject()
    )
  }
}