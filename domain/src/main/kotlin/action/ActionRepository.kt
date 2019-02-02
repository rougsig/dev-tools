package com.github.rougsig.devtools.domain.action

import com.github.rougsig.devtools.core.pairwise
import com.github.rougsig.devtools.domain.LogEntry
import com.github.rougsig.devtools.domain.mappers.toTime
import com.github.rougsig.devtools.network.LogEntryNM
import com.github.rougsig.devtools.network.logLive
import io.reactivex.Observable
import mappers.createDiff
import mappers.getName
import mappers.toField

val actionLive: Observable<LogEntry.Action> = Observable
  .merge(
    Observable.just(LogEntryNM.Action.INIT),
    logLive()
  )
  .ofType(LogEntryNM.Action::class.java)
  .map { it to System.currentTimeMillis() }
  .pairwise()
  .map { (previousLog, nextLog) ->
    val field = nextLog.first.action.toField(nextLog.first.name)
    val previousState = nextLog.first.previousState.toField("State")
    val nextState = nextLog.first.nextState.toField("State")
    val stateDiff = createDiff(previousState, nextState)

    LogEntry.Action(
      name = getName(nextLog.first.name),
      time = (nextLog.second - previousLog.second).toTime(),
      action = field,
      previousState = previousState,
      nextState = nextState,
      stateDiff = stateDiff
    )
  }
