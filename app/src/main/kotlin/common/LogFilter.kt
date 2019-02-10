package com.github.rougsig.devtools.app.common

import javafx.beans.value.ObservableValue
import javafx.collections.ObservableList
import javafx.event.EventTarget
import tornadofx.combobox
import tornadofx.onChange

fun EventTarget.logFilter(
  actionNames: ObservableList<String>,
  changeListener: (String) -> Unit,
  width: ObservableValue<Number>
) {
  combobox<String> {
    prefWidthProperty().bind(width)
    items = actionNames
    isEditable = true

    valueProperty().onChange { changeListener(it ?: "") }
  }
}
