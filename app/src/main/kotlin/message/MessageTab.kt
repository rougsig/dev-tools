package com.github.rougsig.devtools.app.message

import com.github.rougsig.devtools.app.common.clearButton
import com.github.rougsig.devtools.app.store.MessageConnect
import javafx.scene.control.TabPane
import tornadofx.splitpane
import tornadofx.tab
import tornadofx.vbox

fun TabPane.messageTab() {
  tab("message") {
    isClosable = false
    splitpane {
      setDividerPositions(0.3, 0.7)

      vbox {
        messageFilter(widthProperty())
        messageList()
        clearButton(widthProperty(), "message list", MessageConnect::reset)
      }
      messageTabDetails()
    }
  }
}
