package com.github.rougsig.devtools.app.store

import com.github.rougsig.devtools.domain.Field
import com.google.gson.*
import java.lang.reflect.Type

class FieldAdapter : JsonDeserializer<Field.NamedField>, JsonSerializer<Field> {
  override fun serialize(src: Field, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
    return when (src) {
      is Field.ObjectField ->
        JsonObject().apply {
          add("name", JsonPrimitive(src.name))
          add("value", JsonArray(src.value.size).apply {
            src.value.forEach { add(serialize(it, typeOfSrc, context)) }
          })
        }
      is Field.ArrayField ->
        JsonObject().apply {
          add("name", JsonPrimitive(src.name))
          add("value", JsonArray(src.value.size).apply {
            src.value.forEach { add(serialize(it, typeOfSrc, context)) }
          })
        }
      is Field.ValueField ->
        JsonObject().apply {
          add("name", JsonPrimitive(src.name))
          add("value", JsonPrimitive(src.value))
        }
      is Field.NullField ->
        JsonObject().apply {
          add("name", JsonPrimitive(src.name))
        }
      is Field.DiffField ->
        JsonObject().apply {
          add("name", JsonPrimitive(src.name))
          src.value?.let {
            add("value", serialize(it, typeOfSrc, context))
          }
          add("previousValue", serialize(src.previousValue, typeOfSrc, context))
        }
      is Field.AddedField ->
        JsonObject().apply {
          add("name", JsonPrimitive(src.name))
          add("value", serialize(src.value, typeOfSrc, context))
        }
      is Field.RemovedField ->
        JsonObject().apply {
          add("name", JsonPrimitive(src.name))
          add("value", serialize(src.value, typeOfSrc, context))
        }
      else -> throw UnsupportedOperationException("unknown type: $src")
    }.apply {
      add("type", JsonPrimitive(src::class.java.simpleName))
    }
  }

  override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Field.NamedField {
    val obj = json.asJsonObject
    return when (obj.get("type").asString) {
      "ObjectField" ->
        Field.ObjectField(
          name = obj.get("name").asString,
          value = obj.get("value").asJsonArray.map { deserialize(it, typeOfT, context) }
        )
      "ArrayField" ->
        Field.ArrayField(
          name = obj.get("name").asString,
          value = obj.get("value").asJsonArray.map { deserialize(it, typeOfT, context) }
        )
      "ValueField" ->
        Field.ValueField(
          name = obj.get("name").asString,
          value = obj.get("value").asString
        )
      "NullField" ->
        Field.NullField(
          name = obj.get("name").asString
        )
      "DiffField" ->
        Field.DiffField(
          name = obj.get("name").asString,
          value = if (obj.has("value")) deserialize(obj.get("value"), typeOfT, context) else null,
          previousValue = deserialize(obj.get("previousValue"), typeOfT, context)
        )
      "AddedField" ->
        Field.AddedField(
          name = obj.get("name").asString,
          value = deserialize(obj.get("value"), typeOfT, context)
        )
      "RemovedField" ->
        Field.RemovedField(
          name = obj.get("name").asString,
          value = deserialize(obj.get("value"), typeOfT, context)
        )
      else -> throw UnsupportedOperationException("unknown type: $json")
    }
  }
}