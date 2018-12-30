package com.github.rougsig.devtools.network

import com.jakewharton.rxrelay2.PublishRelay
import io.javalin.Javalin
import io.reactivex.Observable

private val ws = Javalin.create()
  .ws("/") { ws ->
    ws.onMessage { _, msg ->
      action.accept(msg)
    }
  }
  .start(1002)

private val action = PublishRelay.create<String>()
fun actionLive(): Observable<String> = action