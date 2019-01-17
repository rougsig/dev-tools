package com.github.rougsig.devtools.domain

sealed class Field {

  abstract class NamedField : Field() {
    abstract val name: String
  }

  data class ObjectField(
    override val name: String,
    val value: List<NamedField>
  ) : NamedField()

  data class ArrayField(
    override val name: String,
    val value: List<NamedField>
  ) : NamedField()

  data class ValueField(
    override val name: String,
    val value: String
  ) : NamedField()

  data class NullField(
    override val name: String
  ) : NamedField()

  data class DiffField(
    override val name: String,
    val value: Field,
    val previousValue: Field
  ) : NamedField()

  data class AddedField(
    override val name: String,
    val value: Field
  ) : NamedField()

  data class RemovedField(
    override val name: String,
    val value: Field
  ) : NamedField()
}

fun Field.isEqualTypeTo(anotherField: Field): Boolean {
  return this.javaClass.simpleName == anotherField.javaClass.simpleName
}

fun Field.isNullType(): Boolean {
  return this is Field.NullField
}