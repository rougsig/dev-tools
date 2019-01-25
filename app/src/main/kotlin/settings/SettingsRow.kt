package com.github.rougsig.devtools.app.settings

import javafx.beans.property.ReadOnlyDoubleProperty
import javafx.event.EventTarget
import tornadofx.button
import tornadofx.fitToParentWidth
import tornadofx.hbox

fun EventTarget.settingsRow(width: ReadOnlyDoubleProperty) {
  hbox {
    fitToParentWidth()

    button("Start recording") {
      minWidthProperty().bind(this@hbox.widthProperty().divide(4))
    }
    button("Pause recording"){
      isVisible = false
      isManaged = false
      minWidthProperty().bind(this@hbox.widthProperty().divide(4))
    }
    button("Import"){
      minWidthProperty().bind(this@hbox.widthProperty().divide(4))
    }
    button("Export"){
      minWidthProperty().bind(this@hbox.widthProperty().divide(4))
    }
    button("v0.0.8"){
      minWidthProperty().bind(this@hbox.widthProperty().divide(4))
    }
  }
}