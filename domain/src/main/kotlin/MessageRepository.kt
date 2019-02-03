package com.github.rougsig.devtools.domain

import com.github.rougsig.devtools.core.pairwise
import com.github.rougsig.devtools.entity.LogEntry
import com.github.rougsig.devtools.entity.LogEntryNM
import io.reactivex.Observable

val messageLive: Observable<LogEntry.Message> = Observable
  .merge(
    Observable.just(LogEntryNM.Message.INIT()),
    logEntryNWLive
  )
  .ofType(LogEntryNM.Message::class.java)
  .pairwise()
  .map { (p, n) ->
    LogEntry.Message(
      name = n.name,
      time = n.time,
      timeDiff = createTimeDiff(p.time, n.time),
      message = n.message
    )
  }
