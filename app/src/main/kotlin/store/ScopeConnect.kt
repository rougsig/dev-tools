package com.github.rougsig.devtools.app.store

import com.github.rougsig.devtools.app.util.select
import com.github.rougsig.devtools.app.util.subscribeOnUi
import com.github.rougsig.devtools.domain.Field
import com.github.rougsig.devtools.domain.Scope
import com.github.rougsig.devtools.domain.scopeLive
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.value.ObservableValue
import javafx.collections.ObservableList
import javafx.collections.transformation.FilteredList
import tornadofx.observable

private val scopes = mutableListOf<Scope>().observable()
private val filteredScopes = FilteredList(scopes) { true }
fun scopes(): ObservableList<Scope> = filteredScopes

private val currentScope = SimpleObjectProperty(Scope.EMPTY)
fun currentScope(): ObservableValue<Scope> = currentScope

private val currentScopeNextField = currentScope.select { it.nextScope }
fun currentScopeNextField(): ObservableValue<Field> = currentScopeNextField

private val currentScopePreviousField = currentScope.select { it.previousScope }
fun currentScopePreviousField(): ObservableValue<Field> = currentScopePreviousField

private val currentScopeDiff = currentScope.select { it.scopeDiff }
fun currentScopeDiff(): ObservableValue<Field?> = currentScopeDiff

private val scopeNames = mutableListOf<String>().observable()
fun scopeNames(): ObservableList<String> = scopeNames

private val onScopeClick = { scope: Scope -> currentScope.set(scope) }
fun onScopeClick(): (Scope) -> Unit = onScopeClick

fun onClearScopeListClick() {
  scopes.setAll(Scope.EMPTY)
  scopeNames.clear()
  currentScope.set(Scope.EMPTY)
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

@Suppress("Unused")
private val connection = scopeLive().subscribeOnUi { scope ->
  scopes.add(0, scope)
  if (!scopeNames.contains(scope.name)) {
    scopeNames.add(scope.name)
  }
}