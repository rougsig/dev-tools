package com.github.rougsig.devtools.domain

import com.github.rougsig.devtools.core.pairwise
import com.github.rougsig.devtools.entity.LogEntry
import io.reactivex.Observable

val logsLive = Observable
  .merge(
    Observable.just(LogEntry.Init()),
    actionLive,
    scopeLive,
    messageLive
  )
  .pairwise()
  .map { (p, n) ->
    n.copy(createTimeDiff(p.time, n.time))
  }
