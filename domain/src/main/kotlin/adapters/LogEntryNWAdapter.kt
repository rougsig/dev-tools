package com.github.rougsig.devtools.domain.adapters

import com.github.rougsig.devtools.entity.LogEntryNM
import com.google.gson.*
import java.lang.reflect.Type
import java.rmi.UnexpectedException

class LogEntryNWAdapter : JsonDeserializer<LogEntryNM>, JsonSerializer<LogEntryNM> {
  override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext): LogEntryNM {
    return when (val type = json.asJsonObject.get("type").asString) {
      LogEntryNM.Action::class.java.simpleName ->
        context.deserialize(json, LogEntryNM.Action::class.java)

      LogEntryNM.ScopeClose::class.java.simpleName ->
        context.deserialize(json, LogEntryNM.ScopeClose::class.java)

      LogEntryNM.ScopeOpen::class.java.simpleName ->
        context.deserialize(json, LogEntryNM.ScopeOpen::class.java)

      else ->
        throw UnexpectedException("unsupported LogEntryNW type: $type")
    }
  }

  override fun serialize(src: LogEntryNM, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
    return context.serialize(src).asJsonObject.apply {
      add("type", JsonPrimitive(src::class.java.simpleName))
    }
  }
}
