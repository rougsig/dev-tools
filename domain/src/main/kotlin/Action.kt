package com.github.rougsig.devtools.domain

data class Action(
  val name: String,
  val fields: List<Field>,
  val diff: List<Pair<Field, Field>>
) {
  companion object {
    val EMPTY = Action(
      "",
      mutableListOf(),
      mutableListOf()
    )
  }

  sealed class Field(
    open val name: String
  ) {
    data class ValueField(
      val value: String
    ) : Field("")

    data class StringField(
      override val name: String,
      val value: String
    ) : Field(name)

    data class ObjectField(
      override val name: String,
      val value: List<Field>
    ) : Field(name)

    data class ArrayField(
      override val name: String,
      val value: List<Field>
    ) : Field(name)
  }
}