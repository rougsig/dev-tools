package com.github.rougsig.devtools.app.store

import com.github.rougsig.devtools.app.util.select
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

private val currentActionField = currentAction.select { it.action }
fun currentActionField(): ObservableValue<Field> = currentActionField

private val actionNames = mutableListOf<String>().observable()
fun actionNames(): ObservableList<String> = actionNames

private val currentDiffField = currentAction.select { it.stateDiff }
fun currentDiffField(): ObservableValue<Field?> = currentDiffField

private val currentActionPreviousState = currentAction.select { it.previousState }
fun currentActionPreviousState(): ObservableValue<Field> = currentActionPreviousState

private val currentActionNextState = currentAction.select { it.nextState }
fun currentActionNextState(): ObservableValue<Field> = currentActionNextState

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