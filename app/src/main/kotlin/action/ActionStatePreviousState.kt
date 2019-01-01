package com.github.rougsig.devtools.app.action

import com.github.rougsig.devtools.app.store.currentActionNewState
import javafx.event.EventTarget

fun EventTarget.actionNewState() = actionFieldsTree(
  currentActionNewState()
)