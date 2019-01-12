package com.github.rougsig.devtools.network

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class LogEntryAdapter : JsonDeserializer<LogEntry> {
  override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext): LogEntry {
    val obj = json.asJsonObject
    val type = LogEntry.Type.valueOf(obj.get("type").asString)

    return when (type) {
      LogEntry.Type.Action -> context.deserialize(json, LogEntry.Action::class.java)
      LogEntry.Type.ScopeOpen -> context.deserialize(json, LogEntry.ScopeOpen::class.java)
      LogEntry.Type.ScopeClose -> context.deserialize(json, LogEntry.ScopeClose::class.java)
    }
  }
}