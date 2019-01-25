package com.github.rougsig.devtools.app.common

import com.github.rougsig.devtools.domain.Field
import javafx.beans.value.ObservableValue
import javafx.event.EventTarget
import javafx.scene.control.Label
import javafx.scene.paint.Color
import tornadofx.box
import tornadofx.px
import tornadofx.scrollpane
import tornadofx.style

private fun <T : Field?> EventTarget.fieldTree(
  field: ObservableValue<T>,
  isScope: Boolean
) {
  val content = scrollpane {

    style {
      focusColor = Color.TRANSPARENT
      backgroundInsets += box(0.px)
    }
  }

  field.addListener { _, _, newValue ->
    content.content = newValue?.toNode(isScope = isScope)
  }
}

fun <T : Field?> EventTarget.fieldTree(
  field: ObservableValue<T>
) = fieldTree(field, false)


fun <T : Field?> EventTarget.scopeFieldTree(
  field: ObservableValue<T>
) = fieldTree(field, true)