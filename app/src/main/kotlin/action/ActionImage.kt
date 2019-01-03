package com.github.rougsig.devtools.app.action

import com.github.rougsig.devtools.app.store.currentActionNextStateImage
import com.github.rougsig.devtools.app.store.currentActionPreviousStateImage
import javafx.beans.value.ObservableValue
import javafx.event.EventTarget
import javafx.scene.image.Image
import javafx.scene.layout.Priority
import tornadofx.*

fun EventTarget.actionImage(
  previousImage: ObservableValue<Image>,
  nextImage: ObservableValue<Image>
) {
  hbox {
    hgrow = Priority.ALWAYS
    imageview(previousImage as ObservableValue<Image?>) {
      isPreserveRatio = true
    }
    imageview(nextImage as ObservableValue<Image?>) {
      isPreserveRatio = true
    }
  }
}

fun EventTarget.actionImage() = actionImage(
  currentActionPreviousStateImage(),
  currentActionNextStateImage()
)