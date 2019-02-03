package com.github.rougsig.devtools.app.store

import com.github.rougsig.devtools.app.util.select
import com.github.rougsig.devtools.domain.actionLive
import com.github.rougsig.devtools.entity.Field
import com.github.rougsig.devtools.entity.LogEntry

object ActionConnect : FilteredListConnect<LogEntry.Action>(actionLive) {
  val action = selectedLogLive.select {
    if (it is LogEntry.Action) {
      it.action
    } else {
      Field.ValueField("Action", "Empty Action")
    }
  }

  val currentState = selectedLogLive.select {
    if (it is LogEntry.Action) {
      it.nextState
    } else {
      Field.ValueField("Action", "Empty Next State")
    }
  }

  val previousState = selectedLogLive.select {
    if (it is LogEntry.Action) {
      it.previousState
    } else {
      Field.ValueField("Action", "Empty Previous State")
    }
  }

  val stateDiff = selectedLogLive.select {
    if (it is LogEntry.Action) {
      it.stateDiff
    } else {
      Field.ValueField("Action", "Empty State Diff")
    }
  }
}
