package com.github.rougsig.devtools.app.action

import com.github.rougsig.devtools.app.common.clearButton
import com.github.rougsig.devtools.app.store.ActionConnect
import javafx.scene.control.TabPane
import tornadofx.splitpane
import tornadofx.tab
import tornadofx.vbox

fun TabPane.actionTab() {
  tab("action") {
    isClosable = false
    splitpane {
      setDividerPositions(0.3, 0.7)

      vbox {
        actionFilter(widthProperty())
        actionList()
        clearButton(widthProperty(), "action list", ActionConnect::reset)
      }
      actionTabDetails()
    }
  }
}
