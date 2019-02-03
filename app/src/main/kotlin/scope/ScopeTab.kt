package com.github.rougsig.devtools.app.scope

import com.github.rougsig.devtools.app.common.clearButton
import com.github.rougsig.devtools.app.store.ScopeConnect
import javafx.scene.control.TabPane
import tornadofx.splitpane
import tornadofx.tab
import tornadofx.vbox

fun TabPane.scopeTab() {
  tab("scope") {
    isClosable = false
    splitpane {
      setDividerPositions(0.3, 0.7)

      vbox {
        scopeFilter(widthProperty())
        scopeList()
        clearButton(widthProperty(), "scope list", ScopeConnect::reset)
      }
      scopeTabDetails()
    }
  }
}
