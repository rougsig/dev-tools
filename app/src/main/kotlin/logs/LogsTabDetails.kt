package com.github.rougsig.devtools.app.logs

import com.github.rougsig.devtools.app.action.actionTabDetails
import com.github.rougsig.devtools.app.message.messageTabDetails
import com.github.rougsig.devtools.app.scope.scopeTabDetails
import com.github.rougsig.devtools.app.store.LogsConnect
import com.github.rougsig.devtools.entity.LogEntry
import javafx.beans.value.ObservableValue
import javafx.event.EventTarget
import javafx.scene.layout.Priority
import tornadofx.hgrow
import tornadofx.vbox

fun EventTarget.logsTabDetails(
  log: ObservableValue<LogEntry>
) {
  vbox {
    hgrow = Priority.ALWAYS

    val action = actionTabDetails(
      LogsConnect.action.action,
      LogsConnect.action.previousState,
      LogsConnect.action.stateDiff,
      LogsConnect.action.currentState
    )

    val scope = scopeTabDetails(
      LogsConnect.scope.previousScope,
      LogsConnect.scope.scopeDiff,
      LogsConnect.scope.currentScope
    )

    val message = messageTabDetails(
      LogsConnect.message.message
    )

    action.managedProperty().bind(action.visibleProperty())
    scope.managedProperty().bind(scope.visibleProperty())
    message.managedProperty().bind(message.visibleProperty())

    action.isVisible = false
    scope.isVisible = false
    message.isVisible = false

    log.addListener { _, _, newValue ->
      when (newValue) {
        is LogEntry.Init -> {
          action.isVisible = false
          scope.isVisible = false
          message.isVisible = false
        }
        is LogEntry.Action -> {
          action.isVisible = true
          scope.isVisible = false
          message.isVisible = false
        }
        is LogEntry.ScopeChange -> {
          action.isVisible = false
          scope.isVisible = true
          message.isVisible = false
        }
        is LogEntry.Message -> {
          action.isVisible = false
          scope.isVisible = false
          message.isVisible = true
        }
      }
    }
  }
}

fun EventTarget.logsTabDetails() = logsTabDetails(
  LogsConnect.selectedLogLive
)
