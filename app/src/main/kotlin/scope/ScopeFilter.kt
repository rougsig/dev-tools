package com.github.rougsig.devtools.app.scope

import com.github.rougsig.devtools.app.store.onScopeFilterChanged
import com.github.rougsig.devtools.app.store.scopeNames
import javafx.beans.value.ObservableValue
import javafx.collections.ObservableList
import javafx.event.EventTarget
import tornadofx.combobox
import tornadofx.onChange

fun EventTarget.scopeFilter(
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

fun EventTarget.scopeFilter(width: ObservableValue<Number>) = scopeFilter(
  scopeNames(),
  onScopeFilterChanged(),
  width
)