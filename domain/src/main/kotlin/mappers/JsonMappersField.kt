package mappers

import com.github.rougsig.devtools.domain.Field
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject

const val DEFAULT_FIELD_NAME = "Root"

internal fun JsonElement.toField(name: String = DEFAULT_FIELD_NAME): Field.NamedField {

  fun JsonObject.toFields(): List<Field.NamedField> {
    return keySet().map { get(it).toField(it) }
  }

  fun JsonArray.toFields(): List<Field.NamedField> {
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
