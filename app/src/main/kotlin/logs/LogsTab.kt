package com.github.rougsig.devtools.app.logs

import com.github.rougsig.devtools.app.common.clearButton
import com.github.rougsig.devtools.app.store.LogsConnect
import javafx.scene.control.TabPane
import tornadofx.splitpane
import tornadofx.tab
import tornadofx.vbox

fun TabPane.logsTab() {
  tab("logs") {
    isClosable = false
    splitpane {
      setDividerPositions(0.3, 0.7)

      vbox {
        logsFilter(widthProperty())
        logsList()
        clearButton(widthProperty(), "logs list", LogsConnect::reset)
      }
      logsTabDetails()
    }
  }
}
