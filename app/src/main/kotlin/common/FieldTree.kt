package com.github.rougsig.devtools.app.common

import com.github.rougsig.devtools.domain.Field
import javafx.beans.value.ObservableValue
import javafx.event.EventTarget
import javafx.scene.control.Label
import tornadofx.scrollpane

fun <T: Field?> EventTarget.fieldTree(
  field: ObservableValue<T>,
  isScope: Boolean =  false
) {
  val content = scrollpane()

  field.addListener { _, _, newValue ->
    content.content = newValue?.toNode(isScope = isScope) ?: Label("No Fields")
  }
}