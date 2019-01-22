package com.github.rougsig.devtools.app.action

import com.github.rougsig.devtools.app.store.onClearActionListClick
import javafx.beans.value.ObservableValue
import javafx.event.EventTarget
import tornadofx.button

fun EventTarget.cleatActionListButton(width: ObservableValue<Number>) {
  button("Clear action list") {
    prefWidthProperty().bind(width)

    setOnMouseClicked {
      onClearActionListClick()
    }
  }
}