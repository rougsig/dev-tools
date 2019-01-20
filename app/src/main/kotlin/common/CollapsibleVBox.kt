package com.github.rougsig.devtools.app.common

import com.github.rougsig.devtools.app.AppStyle
import javafx.scene.Node
import javafx.scene.layout.VBox
import tornadofx.add
import tornadofx.addClass
import tornadofx.hbox
import tornadofx.label

fun collapsibleVBox(
  collapsedLabel: Node,
  expandedLabel: Node,
  content: Node,
  isCollapsed: Boolean = false
): VBox {
  return VBox().apply {
    val collapsedNode = hbox {
      label(">> ").addClass(AppStyle.diffTreeBracketsStyle)
      add(collapsedLabel)
    }
    val expandedNode = hbox {
      label("^^ ").addClass(AppStyle.diffTreeBracketsStyle)
      add(expandedLabel)
    }
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