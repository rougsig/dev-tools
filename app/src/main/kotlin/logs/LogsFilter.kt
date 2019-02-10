package com.github.rougsig.devtools.app.logs

import com.github.rougsig.devtools.app.common.logFilter
import com.github.rougsig.devtools.app.store.LogsConnect
import javafx.beans.value.ObservableValue
import javafx.event.EventTarget

fun EventTarget.logsFilter(width: ObservableValue<Number>) = logFilter(
  LogsConnect.logNames,
  LogsConnect.filterChangeListener,
  width
)
