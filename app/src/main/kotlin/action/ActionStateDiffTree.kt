package com.github.rougsig.devtools.app.action

import com.github.rougsig.devtools.domain.Action
import javafx.event.EventTarget
import javafx.scene.control.TreeItem
import javafx.scene.layout.Priority
import tornadofx.*

fun EventTarget.actionStateDiffTree(
  name: String,
  diffFrom: List<Action.Field>,
  diffTo: List<Action.Field>
) {
  val removedFields = diffFrom.minus(diffTo)
  val addedFields = diffTo.minus(diffFrom)

  val fields = removedFields.plus(addedFields)

  treeview<Action.Field> {
    vgrow = Priority.NEVER
    root = TreeItem(Action.Field.ValueField(name))

    onDoubleClick {
      root.expandAll()
    }

    cellFormat {
      val text = when (it) {
        is Action.Field.ValueField -> it.value
        is Action.Field.ArrayField -> it.name
        is Action.Field.ObjectField -> it.name
        is Action.Field.StringField -> "${it.name}: ${it.value}"
      }
      graphic = label(text) {
        if (removedFields.contains(it)) {
          style {
            backgroundColor += c("#ff0000")
          }
        } else {
          style {
            backgroundColor += c("#00ff00")
          }
        }
      }
    }

    populate { parent ->
      when {
        parent == root -> fields
        parent.value is Action.Field.ObjectField -> (parent.value as Action.Field.ObjectField).value
        parent.value is Action.Field.ArrayField -> (parent.value as Action.Field.ArrayField).value
        else -> null
      }
    }
  }
}