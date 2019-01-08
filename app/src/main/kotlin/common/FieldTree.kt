package com.github.rougsig.devtools.app.common

import com.github.rougsig.devtools.app.AppStyle
import com.github.rougsig.devtools.domain.Field
import javafx.collections.ListChangeListener
import javafx.collections.ObservableList
import javafx.event.EventTarget
import javafx.scene.control.TreeItem
import javafx.scene.layout.Priority
import tornadofx.*

fun EventTarget.fieldTree(
  actionFields: ObservableList<Field>
) {
  treeview<Field> {
    vgrow = Priority.ALWAYS
    root = TreeItem(Field.NameField("Root"))

    onDoubleClick {
      root.expandAll()
    }

    cellFormat {
      graphic = when (it) {
        is Field.NameField -> label(it.name)
        is Field.StringField -> label("${it.name}: ${it.value}")
        is Field.ObjectField -> label(it.name)
        is Field.ArrayField -> label(it.name)
        is Field.DiffField -> hbox {
          if (it.previousValue == null || it.nextValue == null) {
            if (it.previousValue != null && it.nextValue == null) {
              label("${it.name}: ${it.previousValue}") {
                addClass(AppStyle.diffTreeRemoved)
              }
            }
            if (it.nextValue != null && it.previousValue == null) {
              label("${it.name}: ${it.nextValue}") {
                addClass(AppStyle.diffTreeAdded)
              }
            }
          } else {
            label("${it.name}:") {
              style {
                padding = box(0.px, 4.px, 0.px, 0.px)
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
    }

    populate { parent ->
      when {
        parent == root -> actionFields
        parent.value is Field.ObjectField -> (parent.value as Field.ObjectField).value
        parent.value is Field.ArrayField -> (parent.value as Field.ArrayField).value
        else -> null
      }
    }

    actionFields.addListener(ListChangeListener {
      root.expandAll()
    })
  }
}
