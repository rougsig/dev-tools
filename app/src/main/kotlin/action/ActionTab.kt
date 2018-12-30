package com.github.rougsig.devtools.app.action

import javafx.scene.control.TabPane
import tornadofx.hbox
import tornadofx.tab

fun TabPane.actionTab() {
  tab("action") {
    isClosable = false
    hbox {
      actionTabList()
      actionTabDetails()
    }
  }
}