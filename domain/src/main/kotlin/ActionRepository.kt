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
      val field = log.action.toField(log.name)
      val previousState = log.previousState.toField("State")
      val nextState = log.nextState.toField("State")
      val stateDiff = createDiff(previousState, nextState)

      Action(
        name = getName(log.name),
        action = field,
        previousState = previousState,
        nextState = nextState,
        stateDiff = stateDiff
      )
    }
}