package com.github.rougsig.devtools.app

import com.github.rougsig.devtools.app.clipboard.copyClippableLabel
import com.github.rougsig.devtools.app.clipboard.nodeToClipboard
import com.github.rougsig.devtools.domain.stopServer
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.stage.Stage
import tornadofx.App
import tornadofx.onChange

internal class App : App(RootView::class, AppStyle::class) {
  override fun start(stage: Stage) {
    super.start(stage)

    with(stage.scene) {
      focusOwnerProperty().onChange { node ->
        nodeToClipboard(node)
      }

      val ctrlC = KeyCodeCombination(KeyCode.C, KeyCodeCombination.CONTROL_DOWN)
      accelerators[ctrlC] = Runnable {
        copyClippableLabel()
        root.requestFocus()
      }
    }
  }

  override fun stop() {
    super.stop()
    stopServer()
  }
}