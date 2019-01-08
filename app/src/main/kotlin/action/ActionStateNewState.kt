package com.github.rougsig.devtools.app.action

import com.github.rougsig.devtools.app.common.fieldTree
import com.github.rougsig.devtools.app.store.currentActionPreviousState
import javafx.event.EventTarget

fun EventTarget.actionPreviousState() = fieldTree(
  currentActionPreviousState()
)