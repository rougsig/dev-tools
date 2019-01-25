package com.github.rougsig.devtools.app.store

import com.github.rougsig.devtools.app.util.select
import com.github.rougsig.devtools.app.util.subscribeOnUi
import com.github.rougsig.devtools.domain.Field
import com.github.rougsig.devtools.domain.LogEntry
import com.github.rougsig.devtools.domain.action.actionLive
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.value.ObservableValue
import javafx.collections.ObservableList
import javafx.collections.transformation.FilteredList
import tornadofx.observable

private val actionList = mutableListOf<LogEntry>().observable()
fun actionListLive(): ObservableList<LogEntry> = actionList

private val disposable = actionLive.subscribeOnUi { actionList.add(0, it) }

private val filteredActions = FilteredList(actionListLive()) { true }
val actions: ObservableList<LogEntry> = filteredActions

private val currentActionProperty = SimpleObjectProperty<LogEntry>(LogEntry.Init)
val currentAction: ObservableValue<LogEntry> = currentActionProperty

val ObservableValue<LogEntry>.action by lazy {
  currentActionProperty.select {
    if (it is LogEntry.Action) {
      it.action
    } else {
      Field.ValueField("Action", "Empty Action")
    }
  }
}

val ObservableValue<LogEntry>.currentState by lazy {
  currentActionProperty.select {
    if (it is LogEntry.Action) {
      it.nextState
    } else {
      Field.ValueField("Action", "Empty Next State")
    }
  }
}

val ObservableValue<LogEntry>.previousState by lazy {
  currentActionProperty.select {
    if (it is LogEntry.Action) {
      it.previousState
    } else {
      Field.ValueField("Action", "Empty Previous State")
    }
  }
}

val ObservableValue<LogEntry>.stateDiff by lazy {
  currentActionProperty.select {
    if (it is LogEntry.Action) {
      it.stateDiff
    } else {
      Field.ValueField("Action", "Empty State Diff")
    }
  }
}

private val actionNames = mutableListOf<String>().observable()
fun actionNames(): ObservableList<String> = actionNames

private val onActionClick = { action: LogEntry -> currentActionProperty.set(action) }
fun onActionClick(): (LogEntry) -> Unit = onActionClick

fun onClearActionListClick() {
  actionNames.clear()
  currentActionProperty.set(LogEntry.Init)
}

private val onActionFilterChanged = { name: String ->
  filteredActions.setPredicate {
    if (name.isBlank()) {
      true
    } else {
      it.name.contains(name)
    }
  }
}

fun onActionFilterChanged(): (String) -> Unit = onActionFilterChanged
