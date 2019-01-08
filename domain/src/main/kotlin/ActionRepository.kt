package com.github.rougsig.devtools.domain

import com.github.rougsig.devtools.network.LogEntry
import com.github.rougsig.devtools.network.actionLive
import io.reactivex.Observable

fun actionLive(): Observable<Action> {
  return Observable.merge(
    Observable.just(LogEntry.Action.INIT),
    actionLive()
  )
    .map { log ->
      Action(
        name = getName(log.name),
        fields = getFields(log.action),
        previousState = getFields(log.previousState),
        nextState = getFields(log.nextState),
        diff = getDiff(log.previousState, log.nextState),
        time = System.currentTimeMillis().toString()
      )
    }
}