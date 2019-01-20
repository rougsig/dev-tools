package com.github.rougsig.devtools.app.common

import com.github.rougsig.devtools.app.AppStyle
import com.github.rougsig.devtools.domain.Field
import javafx.scene.layout.HBox
import javafx.scene.layout.Region
import javafx.scene.layout.VBox
import tornadofx.add
import tornadofx.addClass
import tornadofx.paddingLeft

const val FIELD_TREE_SPACE = 24

fun Field.toNode(
  skipName: Boolean = false,
  isCollapsed: Boolean = false,
  isScope: Boolean = false
): Region {

  fun Field.ObjectField.toObjectNode(
    skipName: Boolean,
    isCollapsed: Boolean
  ): Region {
    return collapsibleVBox(
      collapsedNode = HBox().apply {
        if (!skipName) {
          nameLabel(name, isScope)
          bracketLabel(": ")
        }
        objectLabel("{...}")
      },
      expandedNode = HBox().apply {
        if (!skipName) {
          nameLabel(name, isScope)
          bracketLabel(": ")
        }
        bracketLabel("{")
      },
      content = VBox().apply {
        value.forEach { add(it.toNode(isScope = isScope)) }
        bracketLabel("}")
      },
      isCollapsed = isCollapsed
    )
  }

  fun Field.ArrayField.toArrayNode(
    skipName: Boolean,
    isCollapsed: Boolean
  ): Region {
    return collapsibleVBox(
      collapsedNode = HBox().apply {
        if (!skipName) {
          nameLabel(name, isScope)
          bracketLabel(": ")
        }
        arrayLabel(value.size.toString())
      },
      expandedNode = HBox().apply {
        if (!skipName) {
          nameLabel(name, isScope)
          bracketLabel(": ")
        }
        bracketLabel("[")
      },
      content = VBox().apply {
        value.forEach { add(it.toNode(isScope = isScope)) }
        bracketLabel("]")
      },
      isCollapsed = isCollapsed
    )
  }

  fun Field.ValueField.toValueNode(
    skipName: Boolean,
    isCollapsed: Boolean
  ): Region {
    return HBox().apply {
      if (!skipName) {
        nameLabel(name, isScope)
        bracketLabel(": ")
      }
      valueLabel(value)
    }
  }

  fun Field.NullField.toNullNode(
    skipName: Boolean,
    isCollapsed: Boolean
  ): Region {
    return HBox().apply {
      if (!skipName) {
        nameLabel(name, isScope)
        bracketLabel(": ")
      }
      valueLabel("null")
    }
  }

  fun Field.DiffField.toDiffNode(
    skipName: Boolean,
    isCollapsed: Boolean
  ): Region {
    return if (value != null) {
      HBox().apply {
        nameLabel(name, isScope)
        bracketLabel(": ")
        add(previousValue.toNode(skipName = true, isScope = isScope).addClass(AppStyle.diffTreeRemoved))
        bracketLabel(" -> ")
        add(value!!.toNode(skipName = true, isScope = isScope).addClass(AppStyle.diffTreeAdded))
      }
    } else {
      previousValue.toNode(isCollapsed = true, isScope = isScope)
    }
  }

  fun Field.AddedField.toAddedNode(
    skipName: Boolean,
    isCollapsed: Boolean
  ): Region {
    return value.toNode(
      skipName,
      isCollapsed,
      isScope
    )
  }

  fun Field.RemovedField.toRemovedNode(
    skipName: Boolean,
    isCollapsed: Boolean
  ): Region {
    return value.toNode(
      skipName,
      isCollapsed,
      isScope
    )
  }

  return when (this) {
    is Field.ObjectField -> toObjectNode(skipName, isCollapsed)
    is Field.ArrayField -> toArrayNode(skipName, isCollapsed)
    is Field.ValueField -> toValueNode(skipName, isCollapsed)
    is Field.NullField -> toNullNode(skipName, isCollapsed)
    is Field.DiffField -> toDiffNode(skipName, isCollapsed)
    is Field.AddedField -> toAddedNode(skipName, isCollapsed)
    is Field.RemovedField -> toRemovedNode(skipName, isCollapsed)
    is Field.NamedField -> throw IllegalStateException("unable to create Node from: NamedField")
  }.apply {
    addClass(AppStyle.diffTreeMainStyle)
    if (!skipName) paddingLeft = FIELD_TREE_SPACE
  }
}