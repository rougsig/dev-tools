package com.github.rougsig.devtools.domain.adapters

import com.github.rougsig.devtools.entity.Field
import com.google.gson.*
import java.lang.reflect.Type

private const val DEFAULT_FIELD_NAME = "Root"

class FieldAdapter : JsonDeserializer<Field>, JsonSerializer<Field> {
  override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): Field {
    return json.toField()
  }

  override fun serialize(src: Field, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
    return src.toJson()
  }

  private fun JsonElement.toField(name: String = DEFAULT_FIELD_NAME): Field {
    fun JsonObject.toFields(): List<Field> {
      return keySet().map { get(it).toField(it) }
    }

    fun JsonArray.toFields(): List<Field> {
      return mapIndexed { i, it -> it.toField("$i") }
    }

    return when {
      isJsonObject -> {
        Field.ObjectField(
          name = name,
          value = asJsonObject.toFields()
        )
      }
      isJsonArray -> {
        Field.ArrayField(
          name = name,
          value = asJsonArray.toFields()
        )
      }
      isJsonPrimitive -> {
        Field.ValueField(
          name = name,
          value = this.asString
        )
      }
      isJsonNull -> {
        Field.NullField(
          name = name
        )
      }
      else -> throw IllegalStateException("unknown type: $this")
    }
  }

  private fun Field.toJson(): JsonElement {
    return when (this) {
      is Field.ObjectField -> {
        JsonObject().apply {
          value.map {
            add(it.name, it.toJson())
          }
        }
      }
      is Field.ArrayField -> {
        JsonArray().apply {
          value.forEach {
            add(it.toJson())
          }
        }
      }
      is Field.ValueField -> {
        JsonPrimitive(value)
      }
      is Field.NullField -> {
        JsonNull.INSTANCE
      }
      is Field.AddedField,
      is Field.RemovedField,
      is Field.DiffField -> throw IllegalStateException("Diff cant be serialized")
    }
  }
}
