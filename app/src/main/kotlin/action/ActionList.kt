package com.github.rougsig.devtools.app.action

import com.github.rougsig.devtools.app.AppStyle
import com.github.rougsig.devtools.app.store.actions
import com.github.rougsig.devtools.app.store.onActionClick
import com.github.rougsig.devtools.domain.sendCommand
import com.github.rougsig.devtools.entity.CommandEntry
import com.github.rougsig.devtools.entity.LogEntry
import javafx.collections.ObservableList
import javafx.event.EventTarget
import javafx.scene.layout.Priority
import tornadofx.*

fun EventTarget.actionList(
  actions: ObservableList<LogEntry>,
  onActionClick: (LogEntry) -> Unit
) {
  listview(actions) {
    hgrow = Priority.ALWAYS
    vgrow = Priority.ALWAYS

    cellFormat { action ->
      graphic = hbox {
        label(action.name) {
          addClass(AppStyle.actionListLabel)
        }
        pane {
          hgrow = Priority.ALWAYS
        }
        if (action is LogEntry.PresenterStateChange) {
          button(action.timeDiff) {
            addClass(AppStyle.actionListLabel)
            setOnMouseClicked {
              sendCommand(
                CommandEntry.SetPresenterState(
                  id = action.presenterId,
                  name = action.presenterName,
                  state = action.nextState
                )
              )
            }
          }
        } else {
          label(action.timeDiff) {
            addClass(AppStyle.actionListLabel)
          }
        }
      }
    }
    onUserSelect(1, onActionClick)
  }
}

fun EventTarget.actionList() = actionList(
  actions,
  onActionClick()
)
