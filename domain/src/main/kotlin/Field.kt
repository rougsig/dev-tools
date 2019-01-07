package com.github.rougsig.devtools.domain

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
    val nextValue: String?
  ) : Field(name)
}