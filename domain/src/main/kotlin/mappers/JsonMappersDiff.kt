package com.github.rougsig.devtools.domain.mappers

import com.github.rougsig.devtools.entity.Field
import com.github.rougsig.devtools.entity.isEqualTypeTo
import com.github.rougsig.devtools.entity.isNullType

internal fun createDiff(previous: Field, next: Field): Field? {
  if (previous.name != next.name) {
    throw IllegalStateException("previous.name != next.name. ${previous.name} != ${next.name}")
  }

  if (!previous.isEqualTypeTo(next) && !previous.isNullType() && !next.isNullType()) {
    val previousClassName = previous.javaClass.simpleName
    val nextClassName = next.javaClass.simpleName
    throw IllegalStateException("unable to create stateDiff: $previousClassName -> $nextClassName")
  }

  if (previous == next) {
    return null
  }

  return when (next) {
    is Field.ObjectField -> {
      when (previous) {
        is Field.ObjectField -> {
          val previousItems = previous.value
          val nextItems = next.value

          val keys = mutableSetOf<String>()

          previousItems.forEach { keys.add(it.name) }
          nextItems.forEach { keys.add(it.name) }

          val diffItems = keys.mapNotNull { name ->
            val previousItem = previousItems.find { it.name == name } ?: Field.NullField(name)
            val nextItem = nextItems.find { it.name == name } ?: Field.NullField(name)

            createDiff(previousItem, nextItem)
          }

          next.copy(
            value = diffItems
          )
        }
        is Field.NullField -> {
          Field.DiffField(
            name = next.name,
            value = next,
            previousValue = previous
          )
        }
        else -> throw IllegalStateException("unable to create stateDiff: $previous -> $next")
      }
    }
    is Field.ArrayField -> {
      when (previous) {
        is Field.NullField -> {
          next.copy(
            value = next.value.mapIndexed { i, it -> Field.AddedField(name = "$i", value = it) }
          )
        }
        is Field.ArrayField -> {
          val previousItems = previous.value
          val nextItems = next.value

          var lastIndex = 0
          val addedItems = nextItems
            .minus(previousItems)
            .mapIndexed { i, it ->
              lastIndex++
              Field.AddedField(name = "$i", value = it)
            }

          val removedItems = previousItems
            .minus(nextItems)
            .mapIndexed { i, it -> Field.RemovedField(name = "${lastIndex + i}", value = it) }

          val diffItems = addedItems.plus(removedItems)

          next.copy(
            value = diffItems
          )
        }
        else -> throw IllegalStateException("unable to create stateDiff: $previous -> $next")
      }
    }
    is Field.ValueField -> {
      when (previous) {
        is Field.ValueField -> {
          Field.DiffField(
            name = next.name,
            value = next,
            previousValue = previous
          )
        }
        is Field.NullField -> {
          Field.DiffField(
            name = next.name,
            value = next,
            previousValue = previous
          )
        }
        else -> throw IllegalStateException("unable to create stateDiff: $previous -> $next")
      }
    }
    is Field.NullField -> {
      when (previous) {
        is Field.ArrayField -> {
          previous.copy(
            value = previous.value.mapIndexed { i, it -> Field.RemovedField(name = "$i", value = it) }
          )
        }
        is Field.ObjectField -> {
          Field.DiffField(
            name = next.name,
            value = next,
            previousValue = previous
          )
        }
        is Field.ValueField -> {
          Field.DiffField(
            name = next.name,
            value = next,
            previousValue = previous
          )
        }
        else -> throw IllegalStateException("unable to create stateDiff: $previous -> $next")
      }
    }
    else -> throw IllegalStateException("unable to create stateDiff: $previous -> $next")
  }
}
