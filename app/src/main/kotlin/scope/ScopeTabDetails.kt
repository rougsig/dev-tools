package com.github.rougsig.devtools.app.scope

import com.github.rougsig.devtools.app.common.scopeFieldTree
import com.github.rougsig.devtools.app.store.ScopeConnect
import com.github.rougsig.devtools.entity.Field
import javafx.beans.value.ObservableValue
import javafx.event.EventTarget
import javafx.scene.control.TabPane
import javafx.scene.layout.Priority
import tornadofx.hgrow
import tornadofx.tab
import tornadofx.tabpane

fun EventTarget.scopeTabDetails(
  previousScope: ObservableValue<Field>,
  scopeDiff: ObservableValue<Field?>,
  currentScope: ObservableValue<Field>
): TabPane {
  return tabpane {
    hgrow = Priority.ALWAYS
    tab("previous scope") {
      isClosable = false
      scopeFieldTree(previousScope)
    }
    tab("scope diff") {
      isClosable = false
      scopeFieldTree(scopeDiff)
    }
    tab("current scope") {
      isClosable = false
      scopeFieldTree(currentScope)
    }
  }
}

fun EventTarget.scopeTabDetails() = scopeTabDetails(
  ScopeConnect.previousScope,
  ScopeConnect.scopeDiff,
  ScopeConnect.currentScope
)
