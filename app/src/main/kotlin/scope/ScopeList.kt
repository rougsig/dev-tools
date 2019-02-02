package com.github.rougsig.devtools.app.scope

import com.github.rougsig.devtools.app.AppStyle
import com.github.rougsig.devtools.app.store.isOpen
import com.github.rougsig.devtools.app.store.onScopeClick
import com.github.rougsig.devtools.app.store.scopes
import com.github.rougsig.devtools.domain.LogEntry
import javafx.collections.ObservableList
import javafx.event.EventTarget
import javafx.scene.layout.Priority
import tornadofx.*

fun EventTarget.scopeList(
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
        label(scope.time) {
          addClass(AppStyle.actionListLabel)
        }

        if (scope.isOpen) {
          addClass(AppStyle.diffTreeAdded)
        } else {
          addClass(AppStyle.diffTreeRemoved)
        }
      }
    }
    onUserSelect(1, onActionClick)
  }
}

fun EventTarget.scopeList() = scopeList(
  scopes,
  onScopeClick()
)
