package com.github.rougsig.devtools.app.store

import com.github.rougsig.devtools.app.util.subscribeOnUi
import com.github.rougsig.devtools.domain.Action
import com.github.rougsig.devtools.domain.ActionRepository
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.value.ObservableValue
import javafx.collections.ObservableList
import tornadofx.observable

private val repo = ActionRepository()

private val actions = mutableListOf<Action>().observable()

fun actions() = actions

private val currentAction = SimpleObjectProperty(Action.EMPTY)
fun currentAction(): ObservableValue<Action> = currentAction

private val currentActionFields = SimpleListProperty<Action.Field>()
fun currentActionFields(): ObservableList<Action.Field> = currentActionFields

private val currentDiffFields =SimpleListProperty<Action.Field>()
fun currentDiffFields(): ObservableList<Action.Field> = currentDiffFields

fun onActionClick(): (Action) -> Unit {
  return { action ->
    currentAction.set(action)
    currentActionFields.set(action.fields.observable())
    currentDiffFields.set(action.diff.observable())
  }
}

private val connection = repo
  .actionLive()
  .subscribeOnUi { action ->
    actions.add(0, action)
  }