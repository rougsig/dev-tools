package com.github.rougsig.devtools.app.store

import com.github.rougsig.devtools.app.util.select
import com.github.rougsig.devtools.app.util.selectList
import com.github.rougsig.devtools.app.util.subscribeOnUi
import com.github.rougsig.devtools.domain.Field
import com.github.rougsig.devtools.domain.Scope
import com.github.rougsig.devtools.domain.scopeLive
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.value.ObservableValue
import javafx.collections.ObservableList

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

@Suppress("Unused")
private val connection = scopeLive().subscribeOnUi { scope ->
  currentScope.set(scope)
}