package com.github.rougsig.devtools.app.scope

import javafx.scene.control.TabPane
import tornadofx.hbox
import tornadofx.tab
import tornadofx.vbox

fun TabPane.scopeTab() {
  tab("scope") {
    isClosable = false
    hbox {
      vbox {
        maxWidth = 360.0
        scopeFilter()
        scopeList()
      }
      scopeTabDetails()
    }
  }
}