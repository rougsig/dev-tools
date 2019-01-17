package com.github.rougsig.devtools.app.common

import com.github.rougsig.devtools.app.AppStyle
import com.github.rougsig.devtools.domain.Field
import javafx.event.EventTarget
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.control.Label
import javafx.scene.control.TreeItem
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import tornadofx.*
import java.lang.IllegalStateException

private const val SPACE = 24

private fun collapsibleVBox(
  collapsedNode: Node,
  expandedNode: Node,
  content: Node,
  isCollapsed: Boolean = false
): VBox {
  return VBox().apply {
    paddingLeft = SPACE

    add(collapsedNode)
    add(expandedNode)
    add(content)

    collapsedNode.managedProperty().bind(collapsedNode.visibleProperty())
    expandedNode.managedProperty().bind(expandedNode.visibleProperty())
    content.managedProperty().bind(content.visibleProperty())

    collapsedNode.setOnMouseClicked {
      content.isVisible = true
      collapsedNode.isVisible = false
      expandedNode.isVisible = true
    }

    expandedNode.setOnMouseClicked {
      content.isVisible = false
      collapsedNode.isVisible = true
      expandedNode.isVisible = false
    }

    collapsedNode.isVisible = isCollapsed
    expandedNode.isVisible = !isCollapsed
    content.isVisible = !isCollapsed
  }
}

fun Field.toNode(skipName: Boolean = false): Node {
  return when (this) {
    is Field.ObjectField -> {
      collapsibleVBox(
        collapsedNode = Label("${if (!skipName) "$name: " else ""}{...}"),
        expandedNode = Label("${if (!skipName) "$name: " else ""}{"),
        content = VBox().apply {
          value.forEach { add(it.toNode()) }
          add(label("}"))
        }
      )
    }
    is Field.ArrayField -> {
      collapsibleVBox(
        collapsedNode = Label("${if (!skipName) "$name: " else ""}Array[${value.size}]"),
        expandedNode = Label("${if (!skipName) "$name: " else ""}["),
        content = VBox().apply {
          value.forEach { add(it.toNode()) }
          add(label("]"))
        }
      )
    }
    is Field.ValueField -> {
      HBox().apply {
        paddingLeft = SPACE
        if (!skipName) {
          add(Label("$name: $value"))
        } else {
          add(Label(value))
        }
      }
    }
    is Field.NullField -> {
      HBox().apply {
        paddingLeft = SPACE
        if (!skipName) {
          add(Label("$name: null"))
        } else {
          add(Label("null"))
        }
      }
    }
    is Field.DiffField -> {
      HBox().apply {
        paddingLeft = SPACE
        add(Label("$name: "))
        add(previousValue.toNode(skipName = true).addClass(AppStyle.diffTreeRemoved))
        add(Label(" -> "))
        add(value.toNode(skipName = true).addClass(AppStyle.diffTreeAdded))
      }
    }
    is Field.AddedField -> {
      value.toNode()
    }
    is Field.RemovedField -> {
      value.toNode()
    }
    is Field.NamedField -> throw IllegalStateException("unable to create Node from: NamedField")
  }.apply {
    addClass(AppStyle.diffTreeMainStyle)
  }
}