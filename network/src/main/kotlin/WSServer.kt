package com.github.rougsig.devtools.network

import com.google.gson.Gson
import com.jakewharton.rxrelay2.PublishRelay
import io.javalin.Javalin
import io.reactivex.Observable

private val gson = Gson()

private val ws = Javalin.create()
  .ws("/") { ws ->
    ws.onMessage { _, msg ->
      val log = gson.fromJson(msg, DevToolsLog::class.java)
      action.accept(log)
    }
  }
  .start(1002)

private val action = PublishRelay.create<DevToolsLog>()
fun logLive(): Observable<DevToolsLog> = action