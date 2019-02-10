package com.github.rougsig.devtools.app.logs

import com.github.rougsig.devtools.app.AppStyle
import com.github.rougsig.devtools.app.store.LogsConnect
import com.github.rougsig.devtools.entity.LogEntry
import javafx.collections.ObservableList
import javafx.event.EventTarget
import javafx.scene.layout.Priority
import tornadofx.*

fun EventTarget.logsList(
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
        when (scope) {
          is LogEntry.Action ->
            addClass(AppStyle.diffTreeAction)
          is LogEntry.ScopeChange ->
            if (scope.isOpen) {
              addClass(AppStyle.diffTreeAdded)
            } else {
              addClass(AppStyle.diffTreeRemoved)
            }
          is LogEntry.Message ->
            addClass(AppStyle.diffTreeMessage)
        }
      }
    }
    onUserSelect(1, onActionClick)
  }
}

fun EventTarget.logsList() = logsList(
  LogsConnect.logListLive,
  LogsConnect.logClickListener
)
