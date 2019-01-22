package com.github.rougsig.devtools.app.common

import com.github.rougsig.devtools.app.AppStyle
import javafx.event.EventTarget
import javafx.scene.paint.Color
import tornadofx.CssRule
import tornadofx.addClass
import tornadofx.label
import tornadofx.style

fun EventTarget.bracketLabel(bracket: String) {
  label(bracket)
    .addClass(AppStyle.diffTreeBracketsStyle)
}

fun EventTarget.arrayLabel(size: String) {
  label("Array[$size]")
    .addClass(AppStyle.diffTreeArrayStyle)
}

fun EventTarget.objectLabel(text: String) {
  label(text)
    .addClass(AppStyle.diffTreeObjectStyle)
}

fun EventTarget.valueLabel(value: String) {
  fun getClassByValue(): CssRule {
    return when (value) {
      "true", "false" -> AppStyle.diffTreeBooleanStyle
      "null" -> AppStyle.diffTreeNullStyle
      else -> AppStyle.diffTreeStringStyle
    }
  }

  label(value)
    .addClass(getClassByValue())
}

fun EventTarget.nameLabel(name: String, isScope: Boolean) {
  fun getClassByName(): CssRule? {
    return when {
      name.toIntOrNull() != null -> AppStyle.diffTreeBracketsStyle
      name !in setOf("Scope", "providers", "children") && isScope -> AppStyle.diffTreeScopeNameStyle
      else -> null
    }
  }

  label(name).apply {
    getClassByName()?.let { addClass(it) }

    setOnMouseClicked {
      requestFocus()
      println("Clicked: $name")
    }

    style {
      focusColor = Color.AQUAMARINE
    }
  }
}
