package com.github.rougsig.devtools.app.scope

import com.github.rougsig.devtools.app.common.scopeFieldTree
import com.github.rougsig.devtools.app.store.currentScope
import com.github.rougsig.devtools.app.store.previousScope
import com.github.rougsig.devtools.app.store.scopeDiff
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
      scopeFieldTree(currentScope.previousScope)
    }
    tab("scope diff") {
      isClosable = false
      scopeFieldTree(currentScope.scopeDiff)
    }
    tab("current scope") {
      isClosable = false
      scopeFieldTree(currentScope.currentScope)
    }
  }
}