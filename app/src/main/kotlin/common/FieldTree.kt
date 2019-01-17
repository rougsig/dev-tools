package com.github.rougsig.devtools.app.common

import com.github.rougsig.devtools.domain.Field
import javafx.beans.value.ObservableValue
import javafx.event.EventTarget
import javafx.scene.control.Label
import tornadofx.*

fun EventTarget.nullableFieldTree(
  field: ObservableValue<Field?>
) {
  val content = scrollpane {
    paddingTop = 24
    paddingBottom = 24
  }

  field.addListener { _, _, newValue ->
    content.content = newValue?.toNode() ?: Label("No Fields")
  }
}

fun EventTarget.fieldTree(
  field: ObservableValue<Field>
) {
  val content = scrollpane {
    paddingTop = 24
    paddingBottom = 24
  }

  field.addListener { _, _, newValue ->
    content.content = newValue?.toNode() ?: Label("No Fields")
  }
}