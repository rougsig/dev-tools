package com.github.rougsig.devtools.network

import com.google.gson.JsonObject

data class DevToolsLog(
  val name: String,
  val action: JsonObject,
  val nextState: JsonObject,
  val previousState: JsonObject,
  val nextStateScreenShot: String,      // Image in Base64
  val previousStateScreenShot: String   // Image in Base64
)