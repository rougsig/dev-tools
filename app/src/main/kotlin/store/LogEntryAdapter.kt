package com.github.rougsig.devtools.app.store

import com.github.rougsig.devtools.domain.LogEntry
import com.google.gson.*
import java.lang.reflect.Type

class LogEntryAdapter : JsonDeserializer<LogEntry>, JsonSerializer<LogEntry> {
  override fun serialize(src: LogEntry, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
    return context.serialize(src).asJsonObject.apply {
      add("type", JsonPrimitive(src::class.java.simpleName))
    }
  }

  override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext): LogEntry {
    val obj = json.asJsonObject
    return when (obj.get("type").asString) {
      "Init" -> LogEntry.Init
      "Action" -> context.deserialize(json, LogEntry.Action::class.java)
      "Scope" -> context.deserialize(json, LogEntry.Scope::class.java)
      else -> throw UnsupportedOperationException("unknown type: $json")
    }
  }
}