package com.github.rougsig.devtools.app.store

import com.github.rougsig.devtools.domain.LogEntry
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.value.ObservableValue

private val appState = SimpleObjectProperty<AppState>(AppState())
fun appState(): ObservableValue<AppState> = appState

data class AppState(
  val isRecording: Boolean = true,
  val logs: List<LogEntry> = emptyList(),
  val actionsLogs: List<LogEntry.Action> = emptyList(),
  val selectedAction: LogEntry.Action? = null,
  val selectedScope: LogEntry.Scope? = null,
  val selectedRoute: LogEntry? = null
)