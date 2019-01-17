package com.github.rougsig.devtools.app.scope

import com.github.rougsig.devtools.app.common.fieldTree
import com.github.rougsig.devtools.app.common.nullableFieldTree
import com.github.rougsig.devtools.app.store.*
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
      fieldTree(currentScopePreviousField())
    }
    tab("scope stateDiff") {
      isClosable = false
      nullableFieldTree(currentScopeDiff())
    }
    tab("next scope") {
      isClosable = false
      fieldTree(currentScopeNextField())
    }
  }
}