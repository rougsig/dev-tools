package com.github.rougsig.devtools.domain

import com.github.rougsig.devtools.network.logLive
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import io.reactivex.Observable

class ActionRepository {
  fun actionLive(): Observable<Action> {
    return logLive().map { log ->
      val name = log.name.split(".").takeLast(2).joinToString(".") { it }
      val fields = getFields(log.obj)

      Action(
        name = name,
        fields = fields
      )
    }
  }

  private fun getFields(obj: JsonObject): List<Action.Field> {
    val fields = mutableListOf<Action.Field>()

    fun getField(el: JsonElement, key: String): Action.Field? {
      return when {
        el.isJsonPrimitive -> Action.Field.StringField(key, el.asString)
        el.isJsonNull -> Action.Field.StringField(key, "null")
        el.isJsonArray -> Action.Field.ArrayField(key, el.asJsonArray.mapNotNull { getField(it, "item") })
        el.isJsonObject -> Action.Field.ObjectField(key, getFields(el.asJsonObject))
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
}