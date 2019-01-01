package com.github.rougsig.devtools.app.util

import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.value.ObservableValue
import javafx.collections.ObservableList
import tornadofx.observable

fun <T, R> ObservableValue<T>.select(selector: (T) -> R): ObservableValue<R> {
  val value = SimpleObjectProperty<R>()

  addListener { _, _, newValue ->
    value.set(selector(newValue))
  }

  return value
}

fun <T, R> ObservableValue<T>.selectList(selector: (T) -> List<R>): ObservableList<R> {
  val list = SimpleListProperty<R>()

  addListener { _, _, newValue ->
    list.set(selector(newValue).observable())
  }

  return list
}