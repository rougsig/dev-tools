package com.github.rougsig.devtools.domain

data class Action(
  val name: String,
  val action: Field,
  val nextState: Field,
  val previousState: Field,
  val stateDiff: Field?
) {
  companion object {
    val EMPTY = Action(
      name = "Action",
      action = Field.NullField("Action"),
      nextState = Field.NullField("State"),
      previousState = Field.NullField("State"),
      stateDiff = Field.NullField("State")
    )
  }
}