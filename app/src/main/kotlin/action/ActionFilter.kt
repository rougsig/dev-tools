package com.github.rougsig.devtools.app.action

import com.github.rougsig.devtools.app.store.actionNames
import com.github.rougsig.devtools.app.store.onActionFilterChanged
import javafx.collections.ObservableList
import javafx.event.EventTarget
import tornadofx.combobox
import tornadofx.fitToParentWidth
import tornadofx.onChange

fun EventTarget.actionFilter(
  actionNames: ObservableList<String>,
  onActionFilterChanged: (String) -> Unit
) {
  combobox<String> {
    fitToParentWidth()
    items = actionNames
    isEditable = true

    valueProperty().onChange { onActionFilterChanged(it ?: "") }
  }
}

fun EventTarget.actionFilter() = actionFilter(
  actionNames(),
  onActionFilterChanged()
)