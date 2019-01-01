package com.github.rougsig.devtools.app.action

import com.github.rougsig.devtools.app.store.currentDiffFields
import javafx.event.EventTarget

fun EventTarget.actionStateDiff() = actionFieldsTree(
  currentDiffFields()
)