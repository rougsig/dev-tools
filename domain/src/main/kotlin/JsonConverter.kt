package com.github.rougsig.devtools.domain

import com.github.rougsig.devtools.domain.adapters.FieldAdapter
import com.github.rougsig.devtools.domain.adapters.LogEntryNWAdapter
import com.github.rougsig.devtools.entity.Field
import com.github.rougsig.devtools.entity.LogEntryNM
import com.google.gson.GsonBuilder
import java.lang.reflect.Type

internal object JsonConverter {
  private val gson = GsonBuilder()
    .registerTypeAdapter(LogEntryNM::class.java, LogEntryNWAdapter())
    .registerTypeAdapter(Field::class.java, FieldAdapter())
    .serializeNulls()
    .create()

  fun <T> fromJson(json: String, typeOfT: Type): T {
    return gson.fromJson<T>(json, typeOfT)
  }

  fun toJson(src: Any, typeOfSrc: Type): String {
    return gson.toJson(src, typeOfSrc)
  }
}
