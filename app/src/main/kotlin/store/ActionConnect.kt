package com.github.rougsig.devtools.app.store

import com.github.rougsig.devtools.app.util.select
import com.github.rougsig.devtools.app.util.selectList
import com.github.rougsig.devtools.app.util.subscribeOnUi
import com.github.rougsig.devtools.domain.Action
import com.github.rougsig.devtools.domain.actionLive
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.value.ObservableValue
import javafx.collections.ObservableList
import javafx.scene.image.Image
import tornadofx.observable

private val actions = mutableListOf<Action>().observable()
fun actions() = actions

private val currentAction = SimpleObjectProperty(Action.EMPTY)
fun currentAction(): ObservableValue<Action> = currentAction

private val currentActionFields = currentAction.selectList { it.fields }
fun currentActionFields(): ObservableList<Action.Field> = currentActionFields

private val currentDiffFields = currentAction.selectList { it.diff }
fun currentDiffFields(): ObservableList<Action.Field> = currentDiffFields

private val currentActionPreviousState = currentAction.selectList { it.previousState }
fun currentActionPreviousState(): ObservableList<Action.Field> = currentActionPreviousState

private val currentActionNextState = currentAction.selectList { it.nextState }
fun currentActionNextState(): ObservableList<Action.Field> = currentActionNextState

private val onActionClick = { action: Action -> currentAction.set(action) }
fun onActionClick(): (Action) -> Unit = onActionClick

@Suppress("Unused")
private val connection = actionLive().subscribeOnUi { action ->
  actions.add(0, action)
}