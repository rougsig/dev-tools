package com.github.rougsig.devtools.app.scope

import javafx.scene.control.TabPane
import tornadofx.tab

fun TabPane.scopeTab() {
  tab("scope") {
    isClosable = false
    scopeTabDetails()
  }
}