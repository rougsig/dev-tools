package com.github.rougsig.devtools.app.message

import com.github.rougsig.devtools.app.common.fieldTree
import com.github.rougsig.devtools.app.store.MessageConnect
import com.github.rougsig.devtools.entity.Field
import javafx.beans.value.ObservableValue
import javafx.event.EventTarget
import javafx.scene.control.TabPane
import javafx.scene.layout.Priority
import tornadofx.hgrow
import tornadofx.tab
import tornadofx.tabpane

fun EventTarget.messageTabDetails(
  message: ObservableValue<Field>
): TabPane {
  return tabpane {
    hgrow = Priority.ALWAYS
    tab("message") {
      isClosable = false
      fieldTree(message)
    }
  }
}

fun EventTarget.messageTabDetails() = messageTabDetails(
  MessageConnect.message
)
