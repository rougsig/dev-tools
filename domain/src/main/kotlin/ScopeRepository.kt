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
      val previousScope = getFields(previousLog.getScopeJson())
      val nextScope = getFields(nextLog.getScopeJson())
      Scope(
        name = nextLog.name,
        list = getProviders(nextScope),
        previousScope = previousScope,
        nextScope = nextScope,
        diff = getDiff(previousLog.getScopeJson(), nextLog.getScopeJson()),
        diffList = getProvidersDiff(previousScope, nextScope),
        isOpen = nextLog.isOpen
      )
    }
}