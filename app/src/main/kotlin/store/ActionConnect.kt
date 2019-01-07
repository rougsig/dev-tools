package com.github.rougsig.devtools.app.store

import com.github.rougsig.devtools.app.util.selectList
import com.github.rougsig.devtools.app.util.subscribeOnUi
import com.github.rougsig.devtools.domain.Action
import com.github.rougsig.devtools.domain.Field
import com.github.rougsig.devtools.domain.actionLive
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.value.ObservableValue
import javafx.collections.ObservableList
import javafx.collections.transformation.FilteredList
import tornadofx.observable

private val actions = mutableListOf<Action>().observable()
private val filteredActions = FilteredList(actions) { true }
fun actions(): ObservableList<Action> = filteredActions

private val currentAction = SimpleObjectProperty(Action.EMPTY)
fun currentAction(): ObservableValue<Action> = currentAction

private val currentActionFields = currentAction.selectList { it.fields }
fun currentActionFields(): ObservableList<Field> = currentActionFields

private val actionNames = mutableListOf<String>().observable()
fun actionNames(): ObservableList<String> = actionNames

private val currentDiffFields = currentAction.selectList { it.diff }
fun currentDiffFields(): ObservableList<Field> = currentDiffFields

private val currentActionPreviousState = currentAction.selectList { it.previousState }
fun currentActionPreviousState(): ObservableList<Field> = currentActionPreviousState

private val currentActionNextState = currentAction.selectList { it.nextState }
fun currentActionNextState(): ObservableList<Field> = currentActionNextState

private val onActionClick = { action: Action -> currentAction.set(action) }
fun onActionClick(): (Action) -> Unit = onActionClick

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

@Suppress("Unused")
private val connection = actionLive().subscribeOnUi { action ->
  actions.add(0, action)
  if (!actionNames.contains(action.name)) {
    actionNames.add(action.name)
  }
}