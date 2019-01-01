package com.github.rougsig.devtools.domain

data class Action(
  val name: String,
  val fields: List<Field>,
  val newState: List<Field>,
  val previousState: List<Field>,
  val diff: List<Field>
) {
  companion object {
    val EMPTY = Action(
      "",
      mutableListOf(),
      mutableListOf(),
      mutableListOf(),
      mutableListOf()
    )
  }

  sealed class Field(
    open val name: String
  ) {
    data class NameField(
      override val name: String
    ) : Field(name)

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

    data class DiffField(
      override val name: String,
      val previousValue: String?,
      val newValue: String?
    ) : Field(name)
  }
}