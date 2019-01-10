package com.github.rougsig.devtools.app.store

import com.github.rougsig.devtools.app.util.selectList
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

private val currentScopeNextFields = currentScope.selectList { it.nextScope }
fun currentScopeNextFields(): ObservableList<Field> = currentScopeNextFields

private val currentScopePreviousFields = currentScope.selectList { it.previousScope }
fun currentScopePreviousFields(): ObservableList<Field> = currentScopePreviousFields

private val currentScopeDiff = currentScope.selectList { it.diff }
fun currentScopeDiff(): ObservableList<Field> = currentScopeDiff

private val currentScopeList = currentScope.selectList { it.list }
fun currentScopeList() = currentScopeList

private val currentScopeDiffList = currentScope.selectList { it.diffList }
fun currentScopeDiffList() = currentScopeDiffList

private val scopeNames = mutableListOf<String>().observable()
fun scopeNames(): ObservableList<String> = scopeNames

private val onScopeClick = { scope: Scope -> currentScope.set(scope) }
fun onScopeClick(): (Scope) -> Unit = onScopeClick

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