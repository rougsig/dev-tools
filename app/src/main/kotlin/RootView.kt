package com.github.rougsig.devtools.app

import com.github.rougsig.devtools.app.action.actionTab
import com.github.rougsig.devtools.app.info.infoTab
import com.github.rougsig.devtools.app.scope.scopeTab
import tornadofx.View
import tornadofx.fitToParentSize
import tornadofx.hbox
import tornadofx.tabpane

private const val HEIGHT = 480.0
private const val WIDTH = 768.0

internal class RootView : View() {
  override val root = hbox {
    minHeight = HEIGHT
    minWidth = WIDTH

    tabpane {
      fitToParentSize()

      actionTab()
      scopeTab()
      infoTab()
    }
  }
}