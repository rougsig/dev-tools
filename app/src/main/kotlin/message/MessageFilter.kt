package com.github.rougsig.devtools.app.message

import com.github.rougsig.devtools.app.common.logFilter
import com.github.rougsig.devtools.app.store.MessageConnect
import javafx.beans.value.ObservableValue
import javafx.event.EventTarget

fun EventTarget.messageFilter(width: ObservableValue<Number>) = logFilter(
  MessageConnect.logNames,
  MessageConnect.filterChangeListener,
  width
)
