package com.github.rougsig.devtools.app.action

import com.github.rougsig.devtools.app.common.fieldTree
import com.github.rougsig.devtools.app.store.currentActionField
import com.github.rougsig.devtools.app.store.currentActionNextState
import com.github.rougsig.devtools.app.store.currentActionPreviousState
import com.github.rougsig.devtools.app.store.currentDiffField
import javafx.event.EventTarget
import javafx.scene.layout.Priority
import tornadofx.*

fun EventTarget.actionTabDetails() {

  tabpane {
    hgrow = Priority.ALWAYS
    tab("action") {
      isClosable = false
      fieldTree(currentActionField())
    }
    tab("state") {
      isClosable = false
      splitpane {
        setDividerPositions(0.33, 0.66, 0.99)

        vbox {
          label("previous") {
            paddingTop = 8
            paddingBottom = 16
          }
          fieldTree(currentActionPreviousState())
        }
        vbox {
          label("diff") {
            paddingTop = 8
            paddingBottom = 16
          }
          fieldTree(currentDiffField())
        }
        vbox {
          label("current") {
            paddingTop = 8
            paddingBottom = 16
          }
          fieldTree(currentActionNextState())
        }
      }
    }
  }
}