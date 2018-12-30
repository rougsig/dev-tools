package com.github.rougsig.devtools.app.action

import com.github.rougsig.devtools.app.store.currentAction
import javafx.beans.value.ObservableValue
import javafx.event.EventTarget
import javafx.scene.layout.Priority
import tornadofx.hgrow
import tornadofx.label

fun EventTarget.actionTabDetails(action: ObservableValue<Action>) {
  label(action) {
    hgrow = Priority.ALWAYS
  }
}

fun EventTarget.actionTabDetails() = actionTabDetails(
  currentAction()
)