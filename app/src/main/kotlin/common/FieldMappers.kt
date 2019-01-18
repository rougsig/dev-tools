package com.github.rougsig.devtools.app.common

import com.github.rougsig.devtools.app.AppStyle
import com.github.rougsig.devtools.domain.Field
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import tornadofx.add
import tornadofx.addClass
import tornadofx.label
import tornadofx.paddingLeft

private const val SPACE = 24

private fun collapsibleVBox(
  collapsedNode: Node,
  expandedNode: Node,
  content: Node,
  isCollapsed: Boolean = false
): VBox {
  return VBox().apply {
    if (!isCollapsed) {
      paddingLeft = SPACE
    }

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


fun Field.toNode(
  skipName: Boolean = false,
  isCollapsed: Boolean = false,
  isScope: Boolean = false
): Node {
  fun isScopeName(name: String) = name !in setOf("Scope", "providers", "children") && isScope

  fun Label.addClassIfScopeName(name: String) = apply {
    if (isScopeName(name)) {
      addClass(AppStyle.diffTreeScopeNameStyle)
    }
  }

  return when (this) {
    is Field.ObjectField -> {
      collapsibleVBox(
        collapsedNode = HBox().apply {
          if (!skipName) {
            add(Label(name).addClassIfScopeName(name))
            add(Label(": ").addClass(AppStyle.diffTreeBracketsStyle))
          }
          add(Label("{...}").addClass(AppStyle.diffTreeObjectStyle))
        },
        expandedNode = HBox().apply {
          if (!skipName) {
            add(Label(name).addClassIfScopeName(name))
            add(Label(": ").addClass(AppStyle.diffTreeBracketsStyle))
          }
          add(Label("{").addClass(AppStyle.diffTreeBracketsStyle))
        },
        content = VBox().apply {
          value.forEach { add(it.toNode(isScope = isScope)) }
          add(label("}").addClass(AppStyle.diffTreeBracketsStyle))
        },
        isCollapsed = isCollapsed
      )
    }
    is Field.ArrayField -> {
      collapsibleVBox(
        collapsedNode = HBox().apply {
          if (!skipName) {
            add(Label(name).addClassIfScopeName(name))
            add(Label(": ").addClass(AppStyle.diffTreeBracketsStyle))
          }
          add(Label("Array[${value.size}]").addClass(AppStyle.diffTreeArrayStyle))
        },
        expandedNode = HBox().apply {
          if (!skipName) {
            add(Label(name).addClassIfScopeName(name))
            add(Label(": ").addClass(AppStyle.diffTreeBracketsStyle))
          }
          add(Label("[").addClass(AppStyle.diffTreeBracketsStyle))
        },
        content = VBox().apply {
          value.forEach { add(it.toNode(isScope = isScope)) }
          add(label("]").addClass(AppStyle.diffTreeBracketsStyle))
        },
        isCollapsed = isCollapsed
      )
    }
    is Field.ValueField -> {
      HBox().apply {
        paddingLeft = SPACE
        if (!skipName) {
          if (name.toIntOrNull() != null) {
            add(Label(name).addClass(AppStyle.diffTreeBracketsStyle))
          } else {
            add(Label(name).addClassIfScopeName(name))
          }
          add(Label(": ").addClass(AppStyle.diffTreeBracketsStyle))
        }
        if (value == "true" || value == "false") {
          add(Label(value).addClass(AppStyle.diffTreeBooleanStyle))
        } else {
          add(Label("\"$value\"").addClass(AppStyle.diffTreeStringStyle))
        }
      }
    }
    is Field.NullField -> {
      HBox().apply {
        paddingLeft = SPACE
        if (!skipName) {
          add(Label(name).addClassIfScopeName(name))
          add(Label(": ").addClass(AppStyle.diffTreeBracketsStyle))
        }
        add(Label("null").addClass(AppStyle.diffTreeNullStyle))
      }
    }
    is Field.DiffField -> {
      HBox().apply {
        paddingLeft = SPACE
        add(Label(name).addClassIfScopeName(name))
        add(Label(": ").addClass(AppStyle.diffTreeBracketsStyle))
        if (value != null) {
          add(previousValue.toNode(skipName = true, isScope = isScope).addClass(AppStyle.diffTreeRemoved))
          add(Label(" -> ").addClass(AppStyle.diffTreeBracketsStyle))
          add(value!!.toNode(skipName = true, isScope = isScope).addClass(AppStyle.diffTreeAdded))
        } else {
          add(previousValue.toNode(skipName = true, isCollapsed = true, isScope = isScope))
        }
      }
    }
    is Field.AddedField -> {
      value.toNode(isScope = isScope)
    }
    is Field.RemovedField -> {
      value.toNode(isScope = isScope)
    }
    is Field.NamedField -> throw IllegalStateException("unable to create Node from: NamedField")
  }.apply {
    addClass(AppStyle.diffTreeMainStyle)
  }
}