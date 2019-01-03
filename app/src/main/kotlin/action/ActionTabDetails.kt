package com.github.rougsig.devtools.app.action

import javafx.event.EventTarget
import javafx.scene.layout.Priority
import tornadofx.hgrow
import tornadofx.tab
import tornadofx.tabpane

fun EventTarget.actionTabDetails() {
  tabpane {
    hgrow = Priority.ALWAYS
    tab("action") {
      isClosable = false
      actionFieldsTree()
    }
    tab("previous state") {
      isClosable = false
      actionPreviousState()
    }
    tab("state diff") {
      isClosable = false
      actionStateDiff()
    }
    tab("next state") {
      isClosable = false
      actionNextState()
    }
    tab("image") {
      actionImage()
    }
  }
}