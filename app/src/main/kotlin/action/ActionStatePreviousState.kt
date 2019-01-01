package com.github.rougsig.devtools.app.action

import com.github.rougsig.devtools.app.store.currentActionNextState
import javafx.event.EventTarget

fun EventTarget.actionNextState() = actionFieldsTree(
  currentActionNextState()
)