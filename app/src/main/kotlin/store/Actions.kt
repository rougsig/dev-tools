package com.github.rougsig.devtools.app.store

import com.github.rougsig.devtools.app.action.Action
import com.github.rougsig.devtools.app.util.subscribeOnUi
import com.github.rougsig.devtools.domain.ActionRepository
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.value.ObservableValue
import tornadofx.observable

private val repo = ActionRepository()

private val actions = mutableListOf<Action>().observable()

private val connection = repo
  .actionLive()
  .subscribeOnUi { action ->
    actions.add(0, Action(action))
  }

fun actions() = actions

private val currentAction = SimpleObjectProperty(Action.EMPTY)

fun currentAction(): ObservableValue<Action> = currentAction

fun onActionClick(): (Action) -> Unit {
  return { action ->
    currentAction.set(action)
  }
}