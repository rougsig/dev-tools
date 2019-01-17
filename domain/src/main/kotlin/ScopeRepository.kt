package com.github.rougsig.devtools.domain

import com.github.rougsig.devtools.core.pairwise
import com.github.rougsig.devtools.network.LogEntry
import com.github.rougsig.devtools.network.scopeLive
import io.reactivex.Observable

fun scopeLive(): Observable<Scope> {
  return Observable
    .merge(
      Observable.just(LogEntry.Scope.INIT),
      scopeLive()
    )
    .pairwise()
    .map { (previousLog, nextLog) ->
      val previousScope = previousLog.scopeJsonObject.toField("Scope")
      val nextScope = nextLog.scopeJsonObject.toField("Scope")
      val scopeDiff = createDiff(previousScope, nextScope)

      Scope(
        name = nextLog.name,
        previousScope = previousScope,
        nextScope = nextScope,
        scopeDiff = scopeDiff,
        isOpen = nextLog.isOpen
      )
    }
}