package com.github.rougsig.devtools.app.common

import javafx.scene.Node
import javafx.scene.layout.VBox
import tornadofx.add

fun collapsibleVBox(
  collapsedNode: Node,
  expandedNode: Node,
  content: Node,
  isCollapsed: Boolean = false
): VBox {
  return VBox().apply {
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