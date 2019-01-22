package com.github.rougsig.devtools.app.action

import javafx.scene.control.TabPane
import tornadofx.splitpane
import tornadofx.tab
import tornadofx.vbox

fun TabPane.actionTab() {
  tab("action logs") {
    isClosable = false
    splitpane {
      setDividerPositions(0.3, 0.7)

      vbox {
        actionFilter(widthProperty())
        actionList()
        cleatActionListButton(widthProperty())
      }
      actionTabDetails()
    }
  }
}