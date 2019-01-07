package com.github.rougsig.devtools.app.scope

import com.github.rougsig.devtools.app.action.actionFieldsTree
import com.github.rougsig.devtools.app.store.currentScopeFields
import javafx.scene.control.TabPane
import tornadofx.hbox
import tornadofx.tab
import tornadofx.vbox

fun TabPane.scopeTab() {
  tab("scope") {
    isClosable = false
    actionFieldsTree(currentScopeFields())
  }
}