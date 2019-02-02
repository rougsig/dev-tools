package com.github.rougsig.devtools.domain.scope

import com.github.rougsig.devtools.core.pairwise
import com.github.rougsig.devtools.domain.LogEntry
import com.github.rougsig.devtools.network.LogEntryNM
import com.github.rougsig.devtools.network.logLive
import io.reactivex.Observable
import mappers.createDiff
import mappers.toField

val scopeLive: Observable<LogEntry.Scope> = Observable
  .merge(
    Observable.just(LogEntryNM.Scope.INIT),
    logLive()
  )
  .ofType(LogEntryNM.Scope::class.java)
  .map { it to System.currentTimeMillis() }
  .pairwise()
  .map { (previousLog, nextLog) ->
    val previousScope = previousLog.first.scopeJsonObject.toField("Scope")
    val nextScope = nextLog.first.scopeJsonObject.toField("Scope")
    val scopeDiff = createDiff(previousScope, nextScope)

    LogEntry.Scope(
      name = nextLog.first.name,
      time = nextLog.second - previousLog.second,
      previousScope = previousScope,
      nextScope = nextScope,
      scopeDiff = scopeDiff,
      isOpen = nextLog.first.isOpen
    )
  }
