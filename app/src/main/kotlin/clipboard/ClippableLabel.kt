package com.github.rougsig.devtools.app.clipboard

import com.github.rougsig.devtools.app.AppStyle
import javafx.event.EventTarget
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.input.Clipboard
import tornadofx.addClass
import tornadofx.label
import tornadofx.putString

private var clipboardValue: String = ""

fun EventTarget.clippableLabel(text: String, op: Label.() -> Unit = {}) {
  label(text) {
    op(this)

    addClass(AppStyle.clippableLabel)
    setOnMouseClicked {
      requestFocus()
    }
  }
}

fun nodeToClipboard(node: Node?) {
  node ?: return
  if (!node.styleClass.contains(AppStyle.clippableLabel.name)) return

  clipboardValue = (node as Label).text
}

fun copyClippableLabel() {
  Clipboard.getSystemClipboard().putString(clipboardValue)
}