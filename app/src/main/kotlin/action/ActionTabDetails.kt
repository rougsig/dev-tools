package com.github.rougsig.devtools.app.action

import com.github.rougsig.devtools.app.common.fieldTree
import com.github.rougsig.devtools.app.store.ActionConnect
import com.github.rougsig.devtools.entity.Field
import javafx.beans.value.ObservableValue
import javafx.event.EventTarget
import javafx.scene.control.TabPane
import javafx.scene.layout.Priority
import tornadofx.*

fun EventTarget.actionTabDetails(
  action: ObservableValue<Field>,
  previousState: ObservableValue<Field>,
  stateDiff: ObservableValue<Field?>,
  currentState: ObservableValue<Field>
): TabPane {
  return tabpane {
    hgrow = Priority.ALWAYS
    tab("action") {
      isClosable = false
      fieldTree(action)
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
          fieldTree(previousState)
        }
        vbox {
          label("diff state") {
            paddingTop = 8
            paddingBottom = 16
            paddingLeft = 16
          }
          fieldTree(stateDiff)
        }
        vbox {
          label("current state") {
            paddingTop = 8
            paddingBottom = 16
            paddingLeft = 16
          }
          fieldTree(currentState)
        }
      }
    }
  }
}

fun EventTarget.actionTabDetails() = actionTabDetails(
  ActionConnect.action,
  ActionConnect.previousState,
  ActionConnect.stateDiff,
  ActionConnect.currentState
)
