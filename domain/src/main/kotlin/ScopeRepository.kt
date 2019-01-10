package com.github.rougsig.devtools.domain

import com.github.rougsig.devtools.core.pairwise
import com.github.rougsig.devtools.network.LogEntry
import com.github.rougsig.devtools.network.scopeLive
import io.reactivex.Observable
import java.util.*

fun scopeLive(): Observable<Scope> {
  return Observable
    .merge(
      Observable.just(LogEntry.Scope.INIT),
      scopeLive()
    )
    .pairwise()
    .map { (previousLog, nextLog) ->
      val previousScope = getFields(previousLog.scope)
      val nextScope = getFields(nextLog.scope)
      Scope(
        name = UUID.randomUUID().toString(),
        list = getProviders(nextScope),
        previousScope = previousScope,
        nextScope = nextScope,
        diff = getDiff(previousLog.scope, nextLog.scope),
        diffList = getProvidersDiff(previousScope, nextScope)
      )
    }
}