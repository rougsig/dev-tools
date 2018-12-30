package com.github.rougsig.devtools.app.action

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
    class StringField(
      name: String,
      val value: String
    ) : Field(name)

    class ListField(
      name: String,
      val value: List<Field>
    ) : Field(name)
  }
}