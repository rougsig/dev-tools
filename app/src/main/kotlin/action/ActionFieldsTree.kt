package com.github.rougsig.devtools.app.action

import com.github.rougsig.devtools.app.store.currentActionFields
import com.github.rougsig.devtools.domain.Action
import javafx.collections.ObservableList
import javafx.event.EventTarget
import javafx.scene.control.TreeItem
import javafx.scene.layout.Priority
import tornadofx.*

fun EventTarget.actionFieldsTree(
  actionFields: ObservableList<Action.Field>
) {
  treeview<Action.Field> {
    vgrow = Priority.ALWAYS
    root = TreeItem(Action.Field.ValueField("Root"))

    onDoubleClick {
      root.expandAll()
    }

    cellFormat {
      text = when (it) {
        is Action.Field.ValueField -> it.value
        is Action.Field.ArrayField -> it.name
        is Action.Field.ObjectField -> it.name
        is Action.Field.StringField -> "${it.name}: ${it.value}"
      }
    }

    populate { parent ->
      when {
        parent == root -> actionFields
        parent.value is Action.Field.ObjectField -> (parent.value as Action.Field.ObjectField).value
        parent.value is Action.Field.ArrayField -> (parent.value as Action.Field.ArrayField).value
        else -> null
      }
    }
  }
}

fun EventTarget.actionFieldsTree() = actionFieldsTree(
  currentActionFields()
)