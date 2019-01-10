package com.github.rougsig.devtools.app.scope

import com.github.rougsig.devtools.app.common.fieldTree
import com.github.rougsig.devtools.app.store.currentScopeNextFields
import javafx.event.EventTarget

fun EventTarget.scopeFieldTree() = fieldTree(
  currentScopeNextFields()
)