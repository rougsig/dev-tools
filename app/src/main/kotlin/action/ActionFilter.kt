package com.github.rougsig.devtools.app.action

import com.github.rougsig.devtools.app.common.logFilter
import com.github.rougsig.devtools.app.store.ActionConnect
import javafx.beans.value.ObservableValue
import javafx.event.EventTarget

fun EventTarget.actionFilter(width: ObservableValue<Number>) = logFilter(
  ActionConnect.logNames,
  ActionConnect.filterChangeListener,
  width
)
