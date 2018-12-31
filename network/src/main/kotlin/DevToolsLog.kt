package com.github.rougsig.devtools.network

import com.google.gson.JsonObject

data class DevToolsLog(
  val name: String,
  val obj: JsonObject
)