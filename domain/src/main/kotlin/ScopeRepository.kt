package com.github.rougsig.devtools.domain

import com.github.rougsig.devtools.network.LogEntry
import com.github.rougsig.devtools.network.scopeLive
import io.reactivex.Observable

fun scopeLive(): Observable<Scope> {
  return Observable.merge(
    Observable.just(LogEntry.Scope.INIT),
    scopeLive()
  )
    .map { log ->
      Scope(
        scope = getFields(log.scope)
      )
    }
}