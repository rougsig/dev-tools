package com.github.rougsig.devtools.app

import com.github.rougsig.devtools.app.action.actionTab
import com.github.rougsig.devtools.app.info.infoTab
import com.github.rougsig.devtools.app.message.messageTab
import com.github.rougsig.devtools.app.scope.scopeTab
import com.github.rougsig.devtools.app.settings.settingsRow
import javafx.scene.layout.Priority
import tornadofx.*

private const val HEIGHT = 480.0
private const val WIDTH = 768.0

internal class RootView : View() {
  override val root = vbox {
    minHeight = HEIGHT
    minWidth = WIDTH

    tabpane {
      vgrow = Priority.ALWAYS
      fitToParentWidth()

      actionTab()
      scopeTab()
      messageTab()
      infoTab()
    }

    settingsRow()
  }
}
