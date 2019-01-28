package com.github.rougsig.devtools.app.settings

import com.github.rougsig.devtools.app.store.exportStore
import com.github.rougsig.devtools.app.store.importStore
import javafx.event.EventTarget
import javafx.scene.Node
import tornadofx.button
import tornadofx.fitToParentWidth
import tornadofx.hbox

fun EventTarget.settingsRow() {
  hbox {
    fitToParentWidth()
    button("Import") {
      minWidthProperty().bind(this@hbox.widthProperty().divide(3))

      setOnMouseClicked {
        importStore((this@settingsRow as Node).scene.window)
      }
    }
    button("Export") {
      minWidthProperty().bind(this@hbox.widthProperty().divide(3))

      setOnMouseClicked {
        exportStore((this@settingsRow as Node).scene.window)
      }
    }
    button("v0.0.8") {
      minWidthProperty().bind(this@hbox.widthProperty().divide(3))
    }
  }
}