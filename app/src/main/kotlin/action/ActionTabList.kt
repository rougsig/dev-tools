package com.github.rougsig.devtools.app.action

import com.github.rougsig.devtools.app.AppStyle
import com.github.rougsig.devtools.app.store.actions
import com.github.rougsig.devtools.app.store.onActionClick
import com.github.rougsig.devtools.domain.Action
import javafx.collections.ObservableList
import javafx.event.EventTarget
import javafx.scene.layout.Priority
import tornadofx.*

fun EventTarget.actionTabList(
  actions: ObservableList<Action>,
  onActionClick: (Action) -> Unit
) {
  listview(actions) {
    hgrow = Priority.ALWAYS
    maxWidth = 360.0
    cellFormat { action ->
      graphic = label(action.name) {
        addClass(AppStyle.actionListLabel)
      }
    }
    onUserSelect(1, onActionClick)
  }
}

fun EventTarget.actionTabList() = actionTabList(
  actions(),
  onActionClick()
)