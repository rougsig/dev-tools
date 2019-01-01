package com.github.rougsig.devtools.domain

import com.github.rougsig.devtools.network.mockActions
import com.google.gson.*
import io.reactivex.Observable

class ActionRepository {
  fun actionLive(): Observable<Action> {
    return Observable.fromIterable(mockActions)
      .map { log ->
        Action(
          name = getName(log.name),
          fields = getFields(log.action),
          diff = getDiff(log.previousState, log.newState)
        )
      }
  }

  private fun getField(el: JsonElement, key: String): Action.Field? {
    return when {
      el.isJsonPrimitive -> Action.Field.StringField(key, el.asString)
      el.isJsonNull -> Action.Field.StringField(key, "null")
      el.isJsonArray -> Action.Field.ArrayField(key, el.asJsonArray.mapIndexedNotNull { i, el -> getField(el, "$i") })
      el.isJsonObject -> Action.Field.ObjectField(key, getFields(el.asJsonObject))
      else -> null
    }
  }

  private fun getFields(obj: JsonObject): List<Action.Field> {
    val fields = mutableListOf<Action.Field>()

    obj.keySet()
      .mapNotNull { key ->
        getField(obj.get(key), key)
      }
      .sortedByDescending { it is Action.Field.StringField }
      .forEach { fields.add(it) }

    return fields
  }

  private fun getDiff(from: JsonObject, to: JsonObject): List<Action.Field> {
    val fields = mutableListOf<Action.Field>()

    val keys = mutableSetOf<String>().apply {
      addAll(from.keySet())
      addAll(to.keySet())
    }

    fun getField(elFrom: JsonElement, elTo: JsonElement, key: String): Action.Field? {
      return when {
        elFrom.isJsonPrimitive && elTo.isJsonPrimitive ->
          Action.Field.StringField(key, "${elFrom.asString} -> ${elTo.asString}")

        elFrom.isJsonArray && elTo.isJsonArray -> {
          val added = elTo.asJsonArray.minus(elFrom.asJsonArray)
          val removed = elFrom.asJsonArray.minus(elTo.asJsonArray)
          val list = mutableListOf<JsonElement>()
            .apply {
              addAll(added)
              addAll(removed)
            }
            .mapNotNull { el ->
              getField(el, if (added.contains(el)) "Added" else "Removed")
            }
          Action.Field.ArrayField(key, list)
        }

        elFrom.isJsonObject && elTo.isJsonObject ->
          Action.Field.ObjectField(key, getDiff(elFrom.asJsonObject, elTo.asJsonObject))

        else -> null
      }
    }

    keys
      .mapNotNull { key ->
        var elFrom = from.get(key) ?: JsonNull.INSTANCE
        var elTo = to.get(key) ?: JsonNull.INSTANCE

        if (elFrom != elTo) {
          if (elFrom == JsonNull.INSTANCE) {
            elFrom = when {
              elTo?.isJsonObject == true -> JsonObject()
              elTo?.isJsonPrimitive == true -> JsonPrimitive("null")
              elTo?.isJsonArray == true -> JsonArray()
              else -> JsonPrimitive("null")
            }
          }

          if (elTo == JsonNull.INSTANCE) {
            elTo = when {
              elFrom.isJsonObject -> JsonObject()
              elFrom.isJsonPrimitive -> JsonPrimitive("null")
              elFrom.isJsonArray -> JsonArray()
              else -> JsonPrimitive("null")
            }
          }
          getField(elFrom, elTo, key)
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
}