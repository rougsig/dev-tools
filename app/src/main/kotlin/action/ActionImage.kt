package com.github.rougsig.devtools.app.action

import com.github.rougsig.devtools.app.store.currentActionPreviousStateImage
import javafx.beans.value.ObservableValue
import javafx.event.EventTarget
import javafx.scene.image.Image
import javafx.scene.layout.Priority
import tornadofx.hgrow
import tornadofx.imageview

fun EventTarget.actionImage(
  image: ObservableValue<Image>,
  width: ObservableValue<Number>,
  height: ObservableValue<Number>
) {
  imageview(image as ObservableValue<Image?>) {
    isPreserveRatio = true
    fitHeightProperty().bind(height)
    hgrow = Priority.ALWAYS
  }
}

fun EventTarget.actionImage(
  width: ObservableValue<Number>,
  height: ObservableValue<Number>
) = actionImage(
  currentActionPreviousStateImage(),
  width,
  height
)