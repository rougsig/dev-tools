package com.github.rougsig.devtools.app.scope

import com.github.rougsig.devtools.app.common.fieldTree
import com.github.rougsig.devtools.app.store.*
import javafx.event.EventTarget
import javafx.scene.layout.Priority
import tornadofx.hgrow
import tornadofx.tab
import tornadofx.tabpane

fun EventTarget.scopeTabDetails() {
  tabpane {
    hgrow = Priority.ALWAYS
    tab("provider diff list") {
      isClosable = false
      fieldTree(currentScopeDiffList())
    }
    tab("provider list") {
      isClosable = false
      fieldTree(currentScopeList())
    }
    tab("previous scope") {
      isClosable = false
      fieldTree(currentScopePreviousFields())
    }
    tab("scope diff") {
      isClosable = false
      fieldTree(currentScopeDiff())
    }
    tab("next scope") {
      isClosable = false
      fieldTree(currentScopeNextFields())
    }
  }
}