package com.github.rougsig.devtools.app.action

import com.github.rougsig.devtools.app.store.currentActionPreviousState
import com.github.rougsig.devtools.app.store.currentDiffFields
import javafx.event.EventTarget

fun EventTarget.actionPreviousState() = actionFieldsTree(
  currentActionPreviousState()
)