package com.github.rougsig.devtools.domain

import com.github.rougsig.devtools.core.pairwise
import com.github.rougsig.devtools.domain.mappers.createDiff
import com.github.rougsig.devtools.entity.LogEntry
import com.github.rougsig.devtools.entity.LogEntryNM
import io.reactivex.Observable

val actionLive: Observable<LogEntry.Action> = Observable
  .merge(
    Observable.just(LogEntryNM.Action.INIT()),
    logEntryNWLive
  )
  .ofType(LogEntryNM.Action::class.java)
  .pairwise()
  .map { (p, n) ->
    LogEntry.Action(
      name = n.name,
      time = n.time,
      timeDiff = createTimeDiff(p.time, n.time),
      action = n.action,
      nextState = n.nextState,
      previousState = n.previousState,
      stateDiff = createDiff(n.previousState, n.nextState)
    )
  }
