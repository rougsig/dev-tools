package com.github.rougsig.devtools.network

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.rmi.UnexpectedException

class LogEntryAdapter : JsonDeserializer<LogEntryNM> {
  override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext): LogEntryNM {
    return when (val type = json.asJsonObject.get("type").asString) {
      LogEntryNM.Action::class.java.simpleName -> context.deserialize(json, LogEntryNM.Action::class.java)
      LogEntryNM.ScopeOpen::class.java.simpleName -> context.deserialize(json, LogEntryNM.ScopeOpen::class.java)
      LogEntryNM.ScopeClose::class.java.simpleName -> context.deserialize(json, LogEntryNM.ScopeClose::class.java)
      else -> throw UnexpectedException("unsupported LogEntry type: $type")
    }
  }
}