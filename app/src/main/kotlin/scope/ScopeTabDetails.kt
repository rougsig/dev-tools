package com.github.rougsig.devtools.app.scope

import com.github.rougsig.devtools.app.common.scopeFieldTree
import com.github.rougsig.devtools.app.store.currentScopeDiff
import com.github.rougsig.devtools.app.store.currentScopeNextField
import com.github.rougsig.devtools.app.store.currentScopePreviousField
import javafx.event.EventTarget
import javafx.scene.layout.Priority
import tornadofx.hgrow
import tornadofx.tab
import tornadofx.tabpane

fun EventTarget.scopeTabDetails() {
  tabpane {
    hgrow = Priority.ALWAYS
    tab("previous scope") {
      isClosable = false
      scopeFieldTree(currentScopePreviousField())
    }
    tab("scope diff") {
      isClosable = false
      scopeFieldTree(currentScopeDiff())
    }
    tab("current scope") {
      isClosable = false
      scopeFieldTree(currentScopeNextField())
    }
  }
}