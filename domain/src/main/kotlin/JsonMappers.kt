package com.github.rougsig.devtools.domain

import com.google.gson.*

internal fun getFields(obj: JsonObject): List<Field> {
  val fields = mutableListOf<Field>()

  fun getField(el: JsonElement, key: String): Field? {
    return when {
      el.isJsonPrimitive ->
        Field.StringField(key, el.asString)

      el.isJsonNull ->
        Field.StringField(key, "null")

      el.isJsonArray ->
        Field.ArrayField(
          key,
          el.asJsonArray
            .mapIndexedNotNull { i, it -> getField(it, "$i") }
        )

      el.isJsonObject ->
        Field.ObjectField(key, getFields(el.asJsonObject))

      else -> null
    }
  }

  obj.keySet()
    .mapNotNull { key ->
      getField(obj.get(key), key)
    }
    .sortedByDescending { it is Field.StringField }
    .forEach { fields.add(it) }

  return fields
}

internal fun JsonElement.jsonNullToJsonType(mapTo: JsonElement): JsonElement {
  return if (this == JsonNull.INSTANCE) {
    when {
      mapTo.isJsonObject -> JsonObject()
      mapTo.isJsonPrimitive -> JsonPrimitive("null")
      mapTo.isJsonArray -> JsonArray()
      else -> JsonPrimitive("null")
    }
  } else {
    this
  }
}

internal fun getFieldDiff(elFrom: JsonElement, elTo: JsonElement, key: String): Field? {
  fun getField(el: JsonElement, key: String, isAdded: Boolean): Field? {
    return when {
      el.isJsonPrimitive ->
        Field.DiffField(
          key,
          if (isAdded) null else el.asString,
          if (isAdded) el.asString else null
        )

      el.isJsonNull ->
        Field.StringField(key, "null")

      el.isJsonArray ->
        Field.ArrayField(
          key,
          el.asJsonArray
            .mapIndexedNotNull { i, it -> getField(it, "$i", isAdded) }
        )

      el.isJsonObject ->
        Field.ObjectField(key, getFields(el.asJsonObject))

      else -> null
    }
  }

  return when {
    elFrom.isJsonPrimitive && elTo.isJsonPrimitive ->
      Field.DiffField(key, elFrom.asString, elTo.asString)

    elFrom.isJsonArray && elTo.isJsonArray -> {
      val added = elTo.asJsonArray.minus(elFrom.asJsonArray)
      val removed = elFrom.asJsonArray.minus(elTo.asJsonArray)
      val list = mutableListOf<JsonElement>()
        .apply {
          addAll(added)
          addAll(removed)
        }
        .mapIndexedNotNull { i, it ->
          getField(it, "$i", added.contains(it))
        }
      Field.ArrayField(key, list)
    }

    elFrom.isJsonObject && elTo.isJsonObject ->
      Field.ObjectField(key, getDiff(elFrom.asJsonObject, elTo.asJsonObject))

    else -> null
  }
}

internal fun getDiff(from: JsonObject, to: JsonObject): List<Field> {
  val fields = mutableListOf<Field>()

  val keys = mutableSetOf<String>().apply {
    addAll(from.keySet())
    addAll(to.keySet())
  }

  keys
    .mapNotNull { key ->
      val elFrom = from.get(key) ?: JsonNull.INSTANCE
      val elTo = to.get(key) ?: JsonNull.INSTANCE

      if (elFrom != elTo) {
        getFieldDiff(
          elFrom.jsonNullToJsonType(elTo),
          elTo.jsonNullToJsonType(elFrom),
          key
        )
      } else {
        null
      }
    }
    .sortedByDescending { it is Field.StringField }
    .forEach { fields.add(it) }

  return fields
}