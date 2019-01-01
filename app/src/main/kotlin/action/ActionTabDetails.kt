package com.github.rougsig.devtools.app.action

import com.github.rougsig.devtools.app.store.currentAction
import com.github.rougsig.devtools.app.util.stringConverter
import com.github.rougsig.devtools.domain.Action
import javafx.beans.value.ObservableValue
import javafx.event.EventTarget
import javafx.scene.layout.Priority
import tornadofx.hgrow
import tornadofx.label
import tornadofx.vbox

fun EventTarget.actionTabDetails(
  action: ObservableValue<Action>
) {
  vbox {
    hgrow = Priority.ALWAYS
    label(
      observable = action,
      converter = stringConverter { action.value.name }
    )
    actionFieldsTree()
    actionStateDiff()
  }
}

fun EventTarget.actionTabDetails() = actionTabDetails(
  currentAction()
)