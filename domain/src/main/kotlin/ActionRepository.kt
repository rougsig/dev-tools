package com.github.rougsig.devtools.domain

import com.github.rougsig.devtools.network.ActionLog
import com.github.rougsig.devtools.network.actionLive
import com.github.rougsig.devtools.network.mockActions
import io.reactivex.Observable

fun actionLive(): Observable<Action> {
  return Observable.merge(
    Observable.just(ActionLog.INIT),
    Observable.fromIterable(mockActions),
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