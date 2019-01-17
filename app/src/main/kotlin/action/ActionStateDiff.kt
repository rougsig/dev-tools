package com.github.rougsig.devtools.app.action

import com.github.rougsig.devtools.app.common.fieldTree
import com.github.rougsig.devtools.app.common.nullableFieldTree
import com.github.rougsig.devtools.app.store.currentDiffField
import javafx.event.EventTarget

fun EventTarget.actionStateDiff() = nullableFieldTree(
  currentDiffField()
)