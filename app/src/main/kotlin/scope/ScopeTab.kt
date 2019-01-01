package com.github.rougsig.devtools.app.scope

import javafx.scene.control.TabPane
import tornadofx.hbox
import tornadofx.label
import tornadofx.tab

fun TabPane.scopeTab() {
  tab("scope logs") {
    isClosable = false
    hbox {
      label("1")
    }
  }
}