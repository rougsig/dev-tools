package com.github.rougsig.devtools.domain

import com.github.rougsig.devtools.core.pairwise
import com.github.rougsig.devtools.domain.mappers.createDiff
import com.github.rougsig.devtools.entity.LogEntry
import com.github.rougsig.devtools.entity.LogEntryNM
import io.reactivex.Observable

val presenterStateChangeLive: Observable<LogEntry.PresenterStateChange> = Observable
  .merge(
    Observable.just(LogEntryNM.PresenterStateChange.INIT()),
    logEntryNWLive
  )
  .ofType(LogEntryNM.PresenterStateChange::class.java)
  .pairwise()
  .map { (p, n) ->
    LogEntry.PresenterStateChange(
      name = n.name,
      time = n.time,
      timeDiff = createTimeDiff(p.time, n.time),
      action = n.action,
      nextState = n.nextState,
      previousState = n.previousState,
      stateDiff = createDiff(n.previousState, n.nextState),
      presenterName = n.presenterName,
      presenterId = n.presenterId
    )
  }
