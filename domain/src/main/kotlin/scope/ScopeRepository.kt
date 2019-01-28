package com.github.rougsig.devtools.domain.scope

import com.github.rougsig.devtools.core.pairwise
import com.github.rougsig.devtools.domain.LogEntry
import com.github.rougsig.devtools.network.LogEntryNM
import com.github.rougsig.devtools.network.logLive
import io.reactivex.Observable
import mappers.createDiff
import mappers.toField

val scopeLive = Observable
  .merge(
    Observable.just(LogEntryNM.Scope.INIT),
    logLive()
  )
  .ofType(LogEntryNM.Scope::class.java)
  .pairwise()
  .map<LogEntry> { (previousLog, nextLog) ->
    val previousScope = previousLog.scopeJsonObject.toField("Scope")
    val nextScope = nextLog.scopeJsonObject.toField("Scope")
    val scopeDiff = createDiff(previousScope, nextScope)

    LogEntry.Scope(
      name = nextLog.name,
      previousScope = previousScope,
      nextScope = nextScope,
      scopeDiff = scopeDiff,
      isOpen = nextLog.isOpen
    )
  }
  .startWith(LogEntry.Init)!!
