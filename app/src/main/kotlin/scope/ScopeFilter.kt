package com.github.rougsig.devtools.app.scope

import com.github.rougsig.devtools.app.store.onScopeFilterChanged
import com.github.rougsig.devtools.app.store.scopeNames
import javafx.collections.ObservableList
import javafx.event.EventTarget
import tornadofx.combobox
import tornadofx.fitToParentWidth
import tornadofx.onChange

fun EventTarget.scopeFilter(
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

fun EventTarget.scopeFilter() = scopeFilter(
  scopeNames(),
  onScopeFilterChanged()
)