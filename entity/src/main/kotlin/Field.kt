package com.github.rougsig.devtools.entity

sealed class Field {
  abstract val name: String

  data class ObjectField(
    override val name: String,
    val value: List<Field>
  ) : Field()

  data class ArrayField(
    override val name: String,
    val value: List<Field>
  ) : Field()

  data class ValueField(
    override val name: String,
    val value: String
  ) : Field()

  data class NullField(
    override val name: String
  ) : Field()

  data class DiffField(
    override val name: String,
    val value: Field?,
    val previousValue: Field
  ) : Field()

  data class AddedField(
    override val name: String,
    val value: Field
  ) : Field()

  data class RemovedField(
    override val name: String,
    val value: Field
  ) : Field()
}

fun Field.isEqualTypeTo(anotherField: Field): Boolean {
  return this.javaClass.simpleName == anotherField.javaClass.simpleName
}

fun Field.isNullType(): Boolean {
  return this is Field.NullField
}
