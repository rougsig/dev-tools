package com.github.rougsig.devtools.app.action

import com.github.rougsig.devtools.app.store.actionNames
import com.github.rougsig.devtools.app.store.onActionFilterChanged
import javafx.beans.value.ObservableValue
import javafx.collections.ObservableList
import javafx.event.EventTarget
import tornadofx.combobox
import tornadofx.onChange

fun EventTarget.actionFilter(
  actionNames: ObservableList<String>,
  onActionFilterChanged: (String) -> Unit,
  width: ObservableValue<Number>
) {
  combobox<String> {
    prefWidthProperty().bind(width)
    items = actionNames
    isEditable = true

    valueProperty().onChange { onActionFilterChanged(it ?: "") }
  }
}

fun EventTarget.actionFilter(width: ObservableValue<Number>) = actionFilter(
  actionNames(),
  onActionFilterChanged(),
  width
)