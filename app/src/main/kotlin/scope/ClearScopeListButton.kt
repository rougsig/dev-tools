package com.github.rougsig.devtools.app.scope

import com.github.rougsig.devtools.app.store.onClearScopeListClick
import javafx.beans.value.ObservableValue
import javafx.event.EventTarget
import tornadofx.button

fun EventTarget.cleatScopeListButton(width: ObservableValue<Number>) {
  button("Clear scope list") {
    prefWidthProperty().bind(width)

    setOnMouseClicked {
      onClearScopeListClick()
    }
  }
}