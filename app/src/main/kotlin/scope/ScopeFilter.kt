package com.github.rougsig.devtools.app.scope

import com.github.rougsig.devtools.app.common.logFilter
import com.github.rougsig.devtools.app.store.ScopeConnect
import javafx.beans.value.ObservableValue
import javafx.event.EventTarget

fun EventTarget.scopeFilter(width: ObservableValue<Number>) = logFilter(
  ScopeConnect.logNames,
  ScopeConnect.filterChangeListener,
  width
)
