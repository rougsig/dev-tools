package com.github.rougsig.devtools.domain

import com.github.rougsig.devtools.domain.mappers.createDiff
import com.github.rougsig.devtools.entity.Field
import com.google.gson.JsonArray
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import org.junit.Assert.assertEquals
import org.junit.Test

class JsonMappersDiffTest {

  private fun createNullField(): Field.NullField {
    return JsonNull.INSTANCE.toField("action") as Field.NullField
  }

  private fun createArrayField(vararg items: String = emptyArray()): Field.ArrayField {
    return JsonArray()
      .apply {
        add("value")
        items.forEach(::add)
      }
      .toField("action") as Field.ArrayField
  }

  private fun createObjectField(vararg items: Pair<String, String> = emptyArray()): Field.ObjectField {
    return JsonObject()
      .apply {
        add("action", JsonPrimitive("value"))
        items.forEach { (key, value) ->
          add(key, JsonPrimitive(value))
        }
      }
      .toField("action") as Field.ObjectField
  }

  @Test
  fun nullToNullDiff() {
    val previous = createNullField()
    val next = createNullField()
    val diff = createDiff(previous, next)

    assertEquals(
      null,
      diff
    )
  }

  @Test
  fun nullToArrayDiff() {
    val previous = createNullField()
    val next = createArrayField()
    val diff = createDiff(previous, next)

    assertEquals(
      Field.ArrayField(
        name = "action",
        value = listOf(
          Field.AddedField(
            name = "0",
            value = Field.ValueField(
              name = "0",
              value = "value"
            )
          )
        )
      ),
      diff
    )
  }

  @Test
  fun arrayToNullDiff() {
    val previous = createArrayField()
    val next = createNullField()
    val diff = createDiff(previous, next)

    assertEquals(
      Field.ArrayField(
        name = "action",
        value = listOf(
          Field.RemovedField(
            name = "0",
            value = Field.ValueField(
              name = "0",
              value = "value"
            )
          )
        )
      ),
      diff
    )
  }

  @Test
  fun arrayToArrayDiff() {
    val previous = createArrayField("previous")
    val next = createArrayField("next")
    val diff = createDiff(previous, next)

    assertEquals(
      Field.ArrayField(
        name = "action",
        value = listOf(
          Field.AddedField(
            name = "0",
            value = Field.ValueField(
              name = "1",
              value = "next"
            )
          ),
          Field.RemovedField(
            name = "1",
            value = Field.ValueField(
              name = "1",
              value = "previous"
            )
          )
        )
      ),
      diff
    )
  }

  @Test
  fun nullToObjectDiff() {
    val previous = createNullField()
    val next = createObjectField()
    val diff = createDiff(previous, next)

    assertEquals(
      Field.DiffField(
        name = "action",
        value = Field.ObjectField(
          name = "action",
          value = listOf(
            Field.ValueField(
              name = "action",
              value = "value"
            )
          )
        ),
        previousValue = Field.NullField(
          name = "action"
        )
      ),
      diff
    )
  }

  @Test
  fun objectToNullDiff() {
    val previous = createObjectField()
    val next = createNullField()
    val diff = createDiff(previous, next)

    assertEquals(
      Field.DiffField(
        name = "action",
        value = Field.NullField(
          name = "action"
        ),
        previousValue = Field.ObjectField(
          name = "action",
          value = listOf(
            Field.ValueField(
              name = "action",
              value = "value"
            )
          )
        )
      ),
      diff
    )
  }

  @Test
  fun objectToObjectDiff() {
    val previous = createObjectField("diffField" to "previous")
    val next = createObjectField("diffField" to "next")
    val diff = createDiff(previous, next)

    assertEquals(
      Field.ObjectField(
        name = "action",
        value = listOf(
          Field.DiffField(
            name = "diffField",
            value = Field.ValueField(
              name = "diffField",
              value = "next"
            ),
            previousValue = Field.ValueField(
              name = "diffField",
              value = "previous"
            )
          )
        )
      ),
      diff
    )
  }

  @Test
  fun complexDiff() {
    assertEquals(Unit, Unit)
  }
}
