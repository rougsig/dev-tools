package com.github.rougsig.devtools.app.store

import com.github.rougsig.devtools.app.util.select
import com.github.rougsig.devtools.domain.actionLive
import com.github.rougsig.devtools.entity.Field
import com.github.rougsig.devtools.entity.LogEntry
import io.reactivex.Observable
import javafx.beans.value.ObservableValue

open class ActionListConnect<T : LogEntry>(live: Observable<T>) : FilteredListConnect<T>(live) {
  val action = selectedLogLive.action

  val currentState = selectedLogLive.currentState

  val previousState = selectedLogLive.previousState

  val stateDiff = selectedLogLive.stateDiff
}

object ActionConnect : ActionListConnect<LogEntry.Action>(actionLive)

val ObservableValue<LogEntry>.action
  get() = select {
    if (it is LogEntry.Action) {
      it.action
    } else {
      Field.ValueField("Action", "Empty Action")
    }
  }

val ObservableValue<LogEntry>.currentState
  get() = select {
    if (it is LogEntry.Action) {
      it.nextState
    } else {
      Field.ValueField("Action", "Empty Next State")
    }
  }

val ObservableValue<LogEntry>.previousState
  get() = select {
    if (it is LogEntry.Action) {
      it.previousState
    } else {
      Field.ValueField("Action", "Empty Previous State")
    }
  }

val ObservableValue<LogEntry>.stateDiff
  get() = select {
    if (it is LogEntry.Action) {
      it.stateDiff
    } else {
      Field.ValueField("Action", "Empty Previous State")
    }
  }
