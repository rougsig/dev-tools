package com.github.rougsig.devtools.app.action

import com.github.rougsig.devtools.app.common.fieldTree
import com.github.rougsig.devtools.app.store.*
import javafx.event.EventTarget
import javafx.scene.layout.Priority
import tornadofx.*

fun EventTarget.actionTabDetails() {

  tabpane {
    hgrow = Priority.ALWAYS
    tab("action") {
      isClosable = false
      fieldTree(currentAction.action)
    }
    tab("state") {
      isClosable = false
      splitpane {
        setDividerPositions(0.33, 0.66, 0.99)

        vbox {
          label("previous state") {
            paddingTop = 8
            paddingBottom = 16
            paddingLeft = 16
          }
          fieldTree(currentAction.previousState)
        }
        vbox {
          label("diff state") {
            paddingTop = 8
            paddingBottom = 16
            paddingLeft = 16
          }
          fieldTree(currentAction.stateDiff)
        }
        vbox {
          label("current state") {
            paddingTop = 8
            paddingBottom = 16
            paddingLeft = 16
          }
          fieldTree(currentAction.currentState)
        }
      }
    }
  }
}