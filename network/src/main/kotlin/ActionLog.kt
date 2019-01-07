package com.github.rougsig.devtools.network

import com.google.gson.JsonObject

data class ActionLog(
  val name: String,
  val action: JsonObject,
  val nextState: JsonObject,
  val previousState: JsonObject,
  val nextStateScreenShot: String? = null,      // Image in Base64
  val previousStateScreenShot: String? = null   // Image in Base64
) {
  companion object {
    val INIT = ActionLog(
      name = "Init",
      action = JsonObject(),
      nextState = JsonObject(),
      previousState = JsonObject()
    )
  }
}