package com.github.rougsig.devtools.app.action

import com.github.rougsig.devtools.app.AppStyle
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
    root = TreeItem(Action.Field.NameField("Root"))

    onDoubleClick {
      root.expandAll()
    }

    cellFormat {
      graphic = when (it) {
        is Action.Field.NameField -> label(it.name)
        is Action.Field.StringField -> label("${it.name}: ${it.value}")
        is Action.Field.ObjectField -> label(it.name)
        is Action.Field.ArrayField -> label(it.name)
        is Action.Field.DiffField -> hbox {
          label("${it.name}:") {
            if (it.previousValue != null && it.nextValue == null) {
              addClass(AppStyle.diffTreeRemoved)
            }
            if (it.nextValue != null && it.previousValue == null) {
              addClass(AppStyle.diffTreeAdded)
            }
          }
          it.previousValue?.let { value ->
            label(value) {
              addClass(AppStyle.diffTreeRemoved)
            }
          }
          if (it.previousValue != null && it.nextValue != null) {
            label(" -> ")
          }
          it.nextValue?.let { value ->
            label(value) {
              style {
                addClass(AppStyle.diffTreeAdded)
              }
            }
          }
        }
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