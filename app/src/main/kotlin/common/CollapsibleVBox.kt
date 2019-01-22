package com.github.rougsig.devtools.app.common

import com.github.rougsig.devtools.app.AppStyle
import javafx.scene.Node
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import tornadofx.add
import tornadofx.addClass
import tornadofx.hbox
import tornadofx.label

private const val ID_CONTROL = "CONTROL"

private fun Pane.findControl(): Node = children.find { it.id == ID_CONTROL }!!

fun collapsibleVBox(
  collapsedLabel: Node,
  expandedLabel: Node,
  content: Node,
  isCollapsed: Boolean = false
): VBox {
  return VBox().apply {
    val collapsedNode = hbox {
      label(">> ") {
        id = ID_CONTROL
        addClass(AppStyle.diffTreeBracketsStyle)
      }
      add(collapsedLabel)
    }
    val expandedNode = hbox {
      label("^^ ") {
        id = ID_CONTROL
        addClass(AppStyle.diffTreeBracketsStyle)
      }
      add(expandedLabel)
    }
    add(content)

    collapsedNode.managedProperty().bind(collapsedNode.visibleProperty())
    expandedNode.managedProperty().bind(expandedNode.visibleProperty())
    content.managedProperty().bind(content.visibleProperty())

    collapsedNode.findControl().setOnMouseClicked {
      content.isVisible = true
      collapsedNode.isVisible = false
      expandedNode.isVisible = true
    }

    expandedNode.findControl().setOnMouseClicked {
      content.isVisible = false
      collapsedNode.isVisible = true
      expandedNode.isVisible = false
    }

    collapsedNode.isVisible = isCollapsed
    expandedNode.isVisible = !isCollapsed
    content.isVisible = !isCollapsed
  }
}