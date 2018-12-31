package com.github.rougsig.devtools.app.action

import com.github.rougsig.devtools.app.store.currentDiffFields
import com.github.rougsig.devtools.domain.Action
import javafx.collections.ObservableList
import javafx.event.EventTarget
import javafx.scene.layout.Priority
import tornadofx.*

fun EventTarget.actionStateDiff(
  diffFields: ObservableList<Pair<Action.Field, Action.Field>>
) {
  listview(diffFields) {
    hgrow = Priority.ALWAYS
    cellFormat { action ->
      graphic = hbox {
        fun addNode(field: Action.Field) {
          when (field) {
            is Action.Field.ValueField -> label(field.value)
            is Action.Field.ArrayField -> actionFieldsTree(field.value.observable())
            is Action.Field.ObjectField -> actionFieldsTree(field.value.observable())
            is Action.Field.StringField -> label(field.value)
          }
        }

        val from = action.first
        val to = action.second
        val name = from.name

        label("$name: ")
        addNode(from)
        label(" -> ")
        addNode(to)
      }
    }
  }
}

fun EventTarget.actionStateDiff() = actionStateDiff(
  currentDiffFields()
)