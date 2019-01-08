package com.github.rougsig.devtools.app.scope

import com.github.rougsig.devtools.app.common.fieldTree
import com.github.rougsig.devtools.app.store.currentScopeFields
import javafx.event.EventTarget

fun EventTarget.scopeFieldTree() = fieldTree(
  currentScopeFields()
)