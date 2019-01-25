package com.github.rougsig.devtools.domain.action

import com.github.rougsig.devtools.domain.LogEntry
import mappers.createDiff
import mappers.getName
import com.github.rougsig.devtools.network.LogEntryNM
import com.github.rougsig.devtools.network.logLive
import mappers.toField

val actionLive = logLive()
  .ofType(LogEntryNM.Action::class.java)
  .map<LogEntry> { log ->
    val field = log.action.toField(log.name)
    val previousState = log.previousState.toField("State")
    val nextState = log.nextState.toField("State")
    val stateDiff = createDiff(previousState, nextState)

    LogEntry.Action(
      name = getName(log.name),
      action = field,
      previousState = previousState,
      nextState = nextState,
      stateDiff = stateDiff
    )
  }
  .startWith(LogEntry.Init)!!
