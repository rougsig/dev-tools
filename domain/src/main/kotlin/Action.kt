package com.github.rougsig.devtools.domain

data class Action(
  val name: String,
  val fields: List<Field>
) {
  companion object {
    val EMPTY = Action(
      "", mutableListOf()
    )
  }

  sealed class Field(
    open val name: String
  ) {
    class ValueField(
      val value: String
    ): Field("")

    class StringField(
      name: String,
      val value: String
    ) : Field(name)

    class ObjectField(
      name: String,
      val value: List<Field>
    ) : Field(name)

    class ArrayField(
      name: String,
      val value: List<Field>
    ) : Field(name)
  }
}