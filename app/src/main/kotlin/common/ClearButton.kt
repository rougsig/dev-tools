package com.github.rougsig.devtools.app.common

import javafx.beans.value.ObservableValue
import javafx.event.EventTarget
import tornadofx.button

fun EventTarget.clearButton(width: ObservableValue<Number>, text: String, clickListener: () -> Unit) {
  button("Clear $text") {
    prefWidthProperty().bind(width)

    setOnMouseClicked { clickListener() }
  }
}
