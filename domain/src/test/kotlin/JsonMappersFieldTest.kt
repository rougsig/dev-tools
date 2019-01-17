package com.github.rougsig.devtools.domain

import com.github.rougsig.devtools.domain.Field.ArrayField
import com.google.gson.JsonArray
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import org.junit.Assert.assertEquals
import org.junit.Test

class JsonMappersFieldTest {
  @Test
  fun jsonNullToField() {
    val json = JsonNull.INSTANCE
    val field = json.toField("action")

    assertEquals(
      Field.NullField(
        name = "action"
      ),
      field
    )
  }

  @Test
  fun jsonPrimitiveToField() {
    val json = JsonPrimitive("value")
    val field = json.toField("action")

    assertEquals(
      Field.ValueField(
        name = "action",
        value = "value"
      ),
      field
    )
  }

  @Test
  fun jsonObjectToField() {
    val json = JsonObject().apply {
      add("action", JsonPrimitive("value"))
    }
    val field = json.toField("action")

    assertEquals(
      Field.ObjectField(
        name = "action",
        value = listOf(
          Field.ValueField(
            name = "action",
            value = "value"
          )
        )
      ),
      field
    )
  }

  @Test
  fun jsonArrayToField() {
    val json = JsonArray().apply {
      add("value")
    }
    val field = json.toField("action")

    assertEquals(
      ArrayField(
        name = "action",
        value = listOf(
          Field.ValueField(
            name = "",
            value = "value"
          )
        )
      ),
      field
    )
  }
}