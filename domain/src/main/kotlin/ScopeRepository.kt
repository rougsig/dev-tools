package com.github.rougsig.devtools.domain

import com.github.rougsig.devtools.network.ScopeLog
import com.github.rougsig.devtools.network.scopeLive
import io.reactivex.Observable

fun scopeLive(): Observable<Scope> {
  return Observable.merge(
    Observable.just(ScopeLog.INIT),
    scopeLive()
  )
    .map { log ->
      Scope(
        scope = getFields(log.scope)
      )
    }
}