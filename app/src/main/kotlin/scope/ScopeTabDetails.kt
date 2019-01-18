package com.github.rougsig.devtools.app.scope

import com.github.rougsig.devtools.app.common.fieldTree
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
      fieldTree(currentScopePreviousField(), isScope = true)
    }
    tab("scope stateDiff") {
      isClosable = false
      fieldTree(currentScopeDiff(), isScope = true)
    }
    tab("next scope") {
      isClosable = false
      fieldTree(currentScopeNextField(), isScope = true)
    }
  }
}