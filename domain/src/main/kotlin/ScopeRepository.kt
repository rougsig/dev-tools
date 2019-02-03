package com.github.rougsig.devtools.domain

import com.github.rougsig.devtools.core.pairwise
import com.github.rougsig.devtools.domain.mappers.createDiff
import com.github.rougsig.devtools.entity.LogEntry
import com.github.rougsig.devtools.entity.LogEntryNM
import io.reactivex.Observable

val scopeLive: Observable<LogEntry.ScopeChange> = Observable
  .merge(
    Observable.just(LogEntryNM.ScopeOpen.INIT()),
    logEntryNWLive
  )
  .ofType(LogEntryNM.Scope::class.java)
  .pairwise()
  .map { (p, n) ->
    LogEntry.ScopeChange(
      name = n.name,
      time = n.time,
      timeDiff = createTimeDiff(p.time, n.time),
      previousScope = p.scope,
      nextScope = n.scope,
      scopeDiff = createDiff(p.scope, n.scope),
      isOpen = n is LogEntryNM.ScopeOpen
    )
  }
