package com.github.rougsig.devtools.app.message

import com.github.rougsig.devtools.app.AppStyle
import com.github.rougsig.devtools.app.store.MessageConnect
import com.github.rougsig.devtools.entity.LogEntry
import javafx.collections.ObservableList
import javafx.event.EventTarget
import javafx.scene.layout.Priority
import tornadofx.*

fun EventTarget.messageList(
  actions: ObservableList<LogEntry>,
  onActionClick: (LogEntry) -> Unit
) {
  listview(actions) {
    hgrow = Priority.ALWAYS
    vgrow = Priority.ALWAYS

    cellFormat { scope ->
      graphic = hbox {
        label(scope.name) {
          addClass(AppStyle.actionListLabel)
        }
        pane {
          hgrow = Priority.ALWAYS
        }
        label(scope.timeDiff) {
          addClass(AppStyle.actionListLabel)
        }
      }
    }
    onUserSelect(1, onActionClick)
  }
}

fun EventTarget.messageList() = messageList(
  MessageConnect.logListLive,
  MessageConnect.logClickListener
)
