package com.github.rougsig.devtools.domain

import com.github.rougsig.devtools.network.logLive
import com.github.rougsig.devtools.network.mockActions
import com.google.gson.*
import io.reactivex.Observable

fun actionLive(): Observable<Action> {
  return Observable.merge(
    Observable.fromIterable(mockActions),
    logLive()
  )
    .map { log ->
      Action(
        name = getName(log.name),
        fields = getFields(log.action),
        previousState = getFields(log.previousState),
        nextState = getFields(log.newState),
        diff = getDiff(log.previousState, log.newState)
      )
    }
}

private fun getFields(obj: JsonObject): List<Action.Field> {
  val fields = mutableListOf<Action.Field>()

  fun getField(el: JsonElement, key: String): Action.Field? {
    return when {
      el.isJsonPrimitive ->
        Action.Field.StringField(key, el.asString)

      el.isJsonNull ->
        Action.Field.StringField(key, "null")

      el.isJsonArray ->
        Action.Field.ArrayField(
          key,
          el.asJsonArray
            .mapIndexedNotNull { i, it -> getField(it, "$i") }
        )

      el.isJsonObject ->
        Action.Field.ObjectField(key, getFields(el.asJsonObject))

      else -> null
    }
  }

  obj.keySet()
    .mapNotNull { key ->
      getField(obj.get(key), key)
    }
    .sortedByDescending { it is Action.Field.StringField }
    .forEach { fields.add(it) }

  return fields
}

private fun JsonElement.jsonNullToJsonType(mapTo: JsonElement): JsonElement {
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

private fun getFieldDiff(elFrom: JsonElement, elTo: JsonElement, key: String): Action.Field? {
  fun getField(el: JsonElement, key: String, isAdded: Boolean): Action.Field? {
    return when {
      el.isJsonPrimitive ->
        Action.Field.DiffField(
          key,
          if (isAdded) null else el.asString,
          if (isAdded) el.asString else null
        )

      el.isJsonNull ->
        Action.Field.StringField(key, "null")

      el.isJsonArray ->
        Action.Field.ArrayField(
          key,
          el.asJsonArray
            .mapIndexedNotNull { i, it -> getField(it, "$i", isAdded) }
        )

      el.isJsonObject ->
        Action.Field.ObjectField(key, getFields(el.asJsonObject))

      else -> null
    }
  }

  return when {
    elFrom.isJsonPrimitive && elTo.isJsonPrimitive ->
      Action.Field.DiffField(key, elFrom.asString, elTo.asString)

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
      Action.Field.ArrayField(key, list)
    }

    elFrom.isJsonObject && elTo.isJsonObject ->
      Action.Field.ObjectField(key, getDiff(elFrom.asJsonObject, elTo.asJsonObject))

    else -> null
  }
}

private fun getDiff(from: JsonObject, to: JsonObject): List<Action.Field> {
  val fields = mutableListOf<Action.Field>()

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
    .sortedByDescending { it is Action.Field.StringField }
    .forEach { fields.add(it) }

  return fields
}

private fun getName(name: String): String {
  return name
    .split(".")
    .takeLast(2)
    .joinToString(".") { it }
}