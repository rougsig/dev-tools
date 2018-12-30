package com.github.rougsig.devtools.app.action

import com.github.rougsig.devtools.app.store.currentAction
import com.github.rougsig.devtools.app.util.stringConverter
import javafx.beans.value.ObservableValue
import javafx.event.EventTarget
import javafx.scene.control.TreeItem
import javafx.scene.layout.Priority
import tornadofx.*

fun EventTarget.actionTabDetails(action: ObservableValue<Action>) {
  val fields = action.value.fields.observable()

  // TODO do it in the right way
  action.onChange {
    fields.setAll(action.value.fields)
  }

  vbox {
    hgrow = Priority.ALWAYS
    label(
      observable = action,
      converter = stringConverter { action.value.name }
    )
    treeview<Action.Field> {
      root = TreeItem(Action.Field.StringField("Root", "Root"))

      cellFormat {
        text = when (it) {
          is Action.Field.ListField -> it.name
          is Action.Field.StringField -> "${it.name}: ${it.value}"
        }
      }

      populate { parent ->
        when {
          parent == root -> fields
          parent.value is Action.Field.ListField -> (parent.value as Action.Field.ListField).value
          else -> null
        }
      }
    }
  }
}

fun EventTarget.actionTabDetails() = actionTabDetails(
  currentAction()
)