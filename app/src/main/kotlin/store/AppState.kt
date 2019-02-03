package com.github.rougsig.devtools.app.store

import com.github.rougsig.devtools.entity.LogEntry
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.value.ObservableValue

private val appState = SimpleObjectProperty<AppState>(AppState())
fun appState(): ObservableValue<AppState> = appState

data class AppState(
  val isRecording: Boolean = true,
  val logs: List<LogEntry> = emptyList(),
  val actionsLogs: List<LogEntry.PresenterStateChange> = emptyList(),
  val selectedPresenterStateChange: LogEntry.PresenterStateChange? = null,
  val selectedScope: LogEntry.ScopeChange? = null,
  val selectedRoute: LogEntry? = null
)
