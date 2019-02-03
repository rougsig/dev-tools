package com.github.rougsig.devtools.domain.adapters

import com.github.rougsig.devtools.entity.CommandEntry
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

class CommandEntryAdapter : JsonSerializer<CommandEntry> {
  override fun serialize(src: CommandEntry, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
    return context.serialize(src).asJsonObject.apply {
      add("type", JsonPrimitive(src.javaClass.simpleName))
    }
  }
}
