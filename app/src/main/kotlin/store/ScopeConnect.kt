package com.github.rougsig.devtools.app.store

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

private val currentScopeFields = currentScope.selectList { it.scope }
fun currentScopeFields(): ObservableList<Field> = currentScopeFields

@Suppress("Unused")
private val connection = scopeLive().subscribeOnUi { scope ->
  currentScope.set(scope)
}