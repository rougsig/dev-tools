package com.github.rougsig.devtools.app.store

import com.github.rougsig.devtools.app.util.select
import com.github.rougsig.devtools.app.util.subscribeOnUi
import com.github.rougsig.devtools.domain.Field
import com.github.rougsig.devtools.domain.LogEntry
import com.github.rougsig.devtools.domain.scope.scopeLive
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.value.ObservableValue
import javafx.collections.ObservableList
import javafx.collections.transformation.FilteredList
import tornadofx.observable

private val scopeList = mutableListOf<LogEntry>().observable()
fun scopeListLive(): ObservableList<LogEntry> = scopeList

private val disposable = scopeLive.subscribeOnUi { scopeList.add(0, it) }

private val filteredScopes = FilteredList(scopeListLive()) { true }
val scopes: ObservableList<LogEntry> = filteredScopes

private val currentScopeProperty = SimpleObjectProperty<LogEntry>(LogEntry.Init)
val currentScope: ObservableValue<LogEntry> = currentScopeProperty

val ObservableValue<LogEntry>.currentScope by lazy {
  currentScopeProperty.select {
    if (it is LogEntry.Scope) {
      it.nextScope
    } else {
      Field.ValueField("Scope", "Empty Current Scope")
    }
  }
}

val ObservableValue<LogEntry>.previousScope by lazy {
  currentScopeProperty.select {
    if (it is LogEntry.Scope) {
      it.previousScope
    } else {
      Field.ValueField("Scope", "Empty Previous Scope")
    }
  }
}

val ObservableValue<LogEntry>.scopeDiff by lazy {
  currentScopeProperty.select {
    if (it is LogEntry.Scope) {
      it.scopeDiff
    } else {
      Field.ValueField("Scope", "Empty Scope Diff")
    }
  }
}

val LogEntry.isOpen: Boolean
  get() {
    return if (this is LogEntry.Scope) {
      this.isOpen
    } else {
      true
    }
  }

private val scopeNames = mutableListOf<String>().observable()
fun scopeNames(): ObservableList<String> = scopeNames

private val onScopeClick = { scope: LogEntry -> currentScopeProperty.set(scope) }
fun onScopeClick(): (LogEntry) -> Unit = onScopeClick

fun onClearScopeListClick() {
  scopeNames.clear()
  currentScopeProperty.set(LogEntry.Init)
}

private val onScopeFilterChanged = { name: String ->
  filteredScopes.setPredicate {
    if (name.isBlank()) {
      true
    } else {
      it.name.contains(name)
    }
  }
}

fun onScopeFilterChanged(): (String) -> Unit = onScopeFilterChanged
