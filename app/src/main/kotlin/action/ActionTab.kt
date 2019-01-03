package com.github.rougsig.devtools.app.action

import javafx.scene.control.TabPane
import tornadofx.hbox
import tornadofx.tab
import tornadofx.vbox

fun TabPane.actionTab() {
  tab("action logs") {
    isClosable = false
    hbox {
      vbox {
        maxWidth = 360.0
        actionFilter()
        actionList()
      }
      actionTabDetails()
    }
  }
}